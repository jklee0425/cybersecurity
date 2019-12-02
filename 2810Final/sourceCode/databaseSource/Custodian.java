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
            myConn = DriverManager.getConnection(AccessControl.accountDatabase, username, password);
            
            
            
        }catch(SQLException e){
            this.loggedIn = false;
            System.out.println("Failed");
            System.exit(0);
        
        }
       loggedIn = true;
        System.out.println("Success!");
    }
    
    //since a custodian has access to creation of users, he can create users
    public boolean createUser(String firstName, String lastName, String newUserName, String newUserPassword, String role, String branch) throws SQLException{
       //error checking
       String formattedBranch = branch.toUpperCase();
        
        //check if the user exists
        String roleUser = role.toUpperCase();
        String sqlCheck = "SELECT userName FROM userInfo WHERE userName = ?";
        PreparedStatement prepStateCheck = myConn.prepareStatement(sqlCheck);
        prepStateCheck.setString(1,AES.encrypt(newUserName,AccessControl.getRoleKey(roleUser)));
        
        myRs = prepStateCheck.executeQuery();
        
        int count = 0;
        while(myRs.next()){
            count++;
        }
        if(count > 0){
            System.out.println("dupe user detected");
            return false;
        }
        
        if(!AccessControl.validBranch(branch)){
            System.out.println("Not a valid branch");
            return false;
        }
        //create user
          String sql = "INSERT INTO userInfo(fName,lName,userName,userPass,role_id, branch) VALUES (?,?,?,?,?,?)";
          PreparedStatement prepareStatement = myConn.prepareStatement(sql);
          prepareStatement.setString(1,firstName);
          prepareStatement.setString(2, lastName);
          prepareStatement.setString(3,AES.encrypt(newUserName, AccessControl.getRoleKey(role.toUpperCase())));
          prepareStatement.setString(4,AES.encrypt(newUserPassword,AccessControl.getRoleKey(role.toUpperCase())));
          prepareStatement.setInt(5,AccessControl.returnRoleID(roleUser));
          prepareStatement.setString(6, formattedBranch);
          //System.out.println(AccessControl.returnRoleID(roleUser) + " ROLE KEY");
          prepareStatement.executeUpdate();
          
          
          return true;
    }
    
    
    public void listUsersByRole(String role){
        String sql = "SELECT * FROM userInfo WHERE role_id = ?";
        try{
            PreparedStatement prepareStatement = myConn.prepareStatement(sql);
            prepareStatement.setInt(1, AccessControl.returnRoleID(role));
            myRs = prepareStatement.executeQuery();
            while(myRs.next()){
              System.out.println(myRs.getString("fName") + " :  " + myRs.getString("lName")+ " : " + myRs.getString("userName") + " : " + myRs.getString("userPass")+":" + myRs.getInt("role_id") );
              
            }
        }catch(SQLException e){
            System.out.println("Error geting sales");
        }
        
    }
    public void viewLogs(){
        try{
            Connection logConn = DriverManager.getConnection(AccessControl.loginDatabase,"sampleuser","CodeHaze1");
            String sql = "SELECT * FROM Logs";
            PreparedStatement prepState = logConn.prepareStatement(sql);
            ResultSet logResults = prepState.executeQuery();
            while(logResults.next()){
                System.out.println(logResults.getString("username") + " " + logResults.getString("action")+ " " + logResults.getString("date"));
            }
            logConn.close();
        }catch(SQLException e){
            System.out.println("ERROR IN RETRIEVING LOGS");
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
    
    private void logEvent(String functionName){
    
    }
}


