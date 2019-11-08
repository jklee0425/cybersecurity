import java.sql.*;
public class Custodian extends SecurityManager implements UserInterface {
    public String role;
    private String username;
    private String password;
    private String currentDatabase;
    private Connection myConn= null;
    
    //implement another connection maybe to connect to the database?
    private Statement myStmt = null;
    private ResultSet myRs = null;
    
    public Custodian(String username, String password){
        //just to initalize the object
        this.username = username;
        this.password = password;
    }
    //for a custodian they will log in directly with a database account
    @Override
    public String login()throws SQLException{
       try{
           //connect to mysql on google cloud
            myConn = DriverManager.getConnection("jdbc:mysql://35.247.4.229:3306/Accounts", username, password);
            this.currentDatabase = "Accounts";
        }catch(SQLException e){
            return "Login failed";
        
        }
        return "Login Successful";  
    }
    
    //since a custodian has access to creation of users, he can create users
    @Override 
    public boolean createUser(String newUserName, String newUserPassword, int role) throws SQLException{
        //set up the user
        String sql = "CREATE USER '?' IDENTIFIED BY '?'";
        PreparedStatement preparedStatement = myConn.prepareStatement(sql);
        preparedStatement.setString(1,newUserName);
        preparedStatement.setString(2,newUserPassword);
        //execute the query
        //set up the grants
        
        System.out.println(preparedStatement);
        return true;
    }
    @Override
    public String showUsers() throws SQLException{
        String sql = "SELECT * from mysql.user";
        PreparedStatement preparedStatement = myConn.prepareStatement(sql);
        myRs = preparedStatement.executeQuery();
        int rowCount = 1;
        System.out.print("list of users");
        //read the results of myRs set
        while(myRs.next()){
            
            System.out.println(rowCount +" : "+ myRs.getString("user"));
            rowCount++;
        }
        return "format string later";
    }
    //since a custodian has access to users, he can list the grants
    @Override
    public String showGrants(String username)throws SQLException{
       String sql = "SHOW GRANTS FOR ?";  
       PreparedStatement preparedStatement = myConn.prepareStatement(sql);
       preparedStatement.setString(1,username);
       myRs = preparedStatement.executeQuery();
       ResultSetMetaData rsmd = myRs.getMetaData();
       String grantUsername = AccessControl.getGrantsUsername(rsmd.getColumnName(1));
       while(myRs.next()){
           
           //System.out.println(myRs.getString("Grants for "+ grantUsername));
       }
        return "Implement showGrants later";
    }
    //will take a predefined list of permissions from an outside jar file
    @Override
    public String grantUser(String username, int role) throws SQLException{
        //set permissions to user in airplane database
        String sql = "GRANT SELECT ON ";
        return "implement grantUser later";
    }
    
    public String createView() throws SQLException{
        this.currentDatabase = "Inventory";
        myConn.close();
        myConn = DriverManager.getConnection("jdbc:mysql://35.247.4.229:3306/"+this.currentDatabase, username, password);
        String sql = "SELECT * FROM Planes";
        PreparedStatement preparedStatement = myConn.prepareStatement(sql);
        myRs = preparedStatement.executeQuery();
        while(myRs.next()){
            System.out.println(myRs.getString("plane_name"));
        }
        return "implement createView later";
    }
    
    public String getTableAll() throws SQLException{
        return "implement warehouse get table all later";
    }
    
    //prevents access from by reflection
    @Override
    public void checkPackageAccess(String pkg){
        if(pkg.equals("java.lang.reflect")){
            throw new SecurityException("You can't do that");
    }
    }
}


