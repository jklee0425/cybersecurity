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
    
    public boolean createUser(String newUserName, String newUserPassword, int role) throws SQLException{
        //set up the user
        String sql = "CREATE USER ? IDENTIFIED BY ?";
        PreparedStatement preparedStatement = myConn.prepareStatement(sql);
        preparedStatement.setString(1,newUserName);
        preparedStatement.setString(2,newUserPassword);
        //execute the query
        //set up the grants
        
        System.out.println(preparedStatement);
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
    
    public boolean addNewEmployee(String fName, String lName, String userName, String password, String role) throws SQLException{
       int whichKey = AccessControl.getRoleKey(role.toUpperCase());
       
        if(AccessControl.validRole(role)){
            String sql = "INSERT INTO userInfo(fName,lName,userName,userPass,role_id) VALUES (?,?,?,?,?)";
            PreparedStatement preparedStatement = myConn.prepareStatement(sql);
            preparedStatement.setString(1, fName);
            preparedStatement.setString(2, lName);
            preparedStatement.setString(3, AES.encrypt(userName,whichKey));
            preparedStatement.setString(4, AES.encrypt(password, whichKey));
            preparedStatement.setInt(5,AccessControl.returnRole(role));
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        return true;
        }else{
            System.out.println("INVALID ROLE");
            return false;
        }
  
        
        
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


