import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Custodian extends SecurityManager {
    public String role = "Custodian";
    private boolean loggedIn;

    private String currentDatabase;
    private Connection myConn= null;
    private List<Integer> keys;
    private Statement myStmt = null;
    private ResultSet myRs = null;
    
    public Custodian(String username, String password) throws SQLException{
        //just to initalize the object
        login(username,password);
        
    }
    //for a custodian they will log in directly with a database account

    private void login(String username,String password){
       try{
           keys = new ArrayList<>();
           getKeys(username,password);
           //first obtain the keys
            myConn = DriverManager.getConnection("jdbc:mysql://35.247.4.229:3306/Accounts", username, password);
            
            
            
        }catch(SQLException e){
            this.loggedIn = false;
            System.out.println("Failed");
        
        }
       loggedIn = true;
        System.out.println("Success!");
    }
    
    //since a custodian has access to creation of users, he can create users
    public boolean createUser(String firstName, String lastName, String newUserName, String newUserPassword, String role) throws SQLException{
        //check if the user exists
        String sqlCheck = "SELECT fName, lName FROM userInfo WHERE fName = ? AND lName = ?";
        PreparedStatement prepStateCheck = myConn.prepareStatement(sqlCheck);
        prepStateCheck.setString(1,firstName);
        prepStateCheck.setString(2, lastName);
        myRs = prepStateCheck.executeQuery();
        boolean dupeUser = false;
        int count = 0;
        while(myRs.next()){
            count++;
        }
        if(count > 0){
            System.out.println("dupe user detected");
            return false;
        }
        
        //create user
          String sql = "INSERT INTO userInfo(fName,lName,userName,userPass,role_id) VALUES (?,?,?,?,?)";
          PreparedStatement prepareStatement = myConn.prepareStatement(sql);
          prepareStatement.setString(1,firstName);
          prepareStatement.setString(2, lastName);
          prepareStatement.setString(3,AES.encrypt(newUserName, AccessControl.getRoleKey(role.toUpperCase())));
          prepareStatement.setString(4,AES.encrypt(newUserPassword,AccessControl.getRoleKey(role.toUpperCase())));
          prepareStatement.setInt(5,AccessControl.returnRoleID(role));
          //System.out.println(AccessControl.getRoleKey(role) + " ROLE KEY");
          prepareStatement.executeUpdate();
          
          
          return true;
    }
    
    public String showUsers() throws SQLException{
        String sql = "SELECT * from mysql.user";
        PreparedStatement preparedStatement = myConn.prepareStatement(sql);
        myRs = preparedStatement.executeQuery();
        int rowCount = 1;
        System.out.println("List of Users");
        //read the results of myRs set
        while(myRs.next()){
            
            System.out.println("     " + rowCount +" : "+ myRs.getString("user"));
            rowCount++;
        }
        return "format string later";
    }
    //since a custodian has access to users, he can list the grants

    public String showGrants(String username)throws SQLException{
       String sql = "SHOW GRANTS FOR ?";  
       PreparedStatement preparedStatement = myConn.prepareStatement(sql);
       preparedStatement.setString(1,username);
       myRs = preparedStatement.executeQuery();
       ResultSetMetaData rsmd = myRs.getMetaData();
       String grantUsername = AccessControl.getGrantsUsername(rsmd.getColumnName(1));
       while(myRs.next()){
           
           System.out.println(myRs.getString("Grants for "+ grantUsername));
       }
        return "Implement showGrants later";
    }
    //will take a predefined list of permissions from an outside jar file
    
    public String grantUser(String username, int role) throws SQLException{
        //set permissions to user in airplane database
        String sql = "GRANT SELECT ON ";
        return "implement grantUser later";
    }
    

    
   
    
    public boolean isLoggedIn() {
        return this.loggedIn;
    }
    
    public boolean logOff() throws SQLException {
        myConn.close();
        this.loggedIn = false;
        return this.loggedIn;
    }
    
    //retrieve the keys from the login database
    private void getKeys(String username, String password){
        try{
        Connection keyConn = DriverManager.getConnection(AccessControl.loginDatabase,username,password);
        String sql = "SELECT key_val FROM roles";
        PreparedStatement preparedStatement = keyConn.prepareStatement(sql);
        ResultSet keyRs = preparedStatement.executeQuery();
        while(keyRs.next()){
            int key = keyRs.getInt("key_val");
            this.keys.add(key);
        }
        
        
        }catch(SQLException e){
        
        }
    }
    
    
    
    //prevents access from by reflection
    @Override
    public void checkPackageAccess(String pkg){
        if(pkg.equals("java.lang.reflect")){
            throw new SecurityException("You can't do that");
    }
    }
    
    
}


