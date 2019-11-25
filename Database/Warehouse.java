
import java.sql.*;
import java.util.ArrayList;


public class Warehouse {
    private String role = "WAREHOUSE";
    private int key;
    private boolean loggedIn;
    private Connection myConn = null;
    private Statement myStmt = null;
    private ResultSet myRs = null;
   
   
    private static String dbEmp;
    private static String dbPass;
    
    public Warehouse(String username, String password){
        
        this.dbEmp = "login_agent";
        this.dbPass = "agent_login@";
        assignKey();
        login(username,password);
        
     
}
    
    
    
    private void login(String username, String password) {
       try{
           String encUsername = AES.encrypt(username,this.key);
           String encPassword = AES.encrypt(password, this.key);
           Connection accountAgent = DriverManager.getConnection(AccessControl.accountDatabase,dbEmp,dbPass);
           String accSql = "SELECT userName,userPass FROM userInfo WHERE userName = ? AND userPass = ?";
           PreparedStatement prepState = accountAgent.prepareStatement(accSql);
           prepState.setString(1,encUsername);
           prepState.setString(2,encPassword);
           ResultSet accRs = prepState.executeQuery();
           int count = 0;
           while(accRs.next()){
               count++;
           }
           if(count == 1){
               System.out.println("VERIFIED");
           }
           /*
           myConn = DriverManager.getConnection(AccessControl.accountDatabase, dbEmp, dbPass);
           String sql = "SELECT userName, userPass FROM userInfo WHERE userName = ? AND userPass = ?";
           PreparedStatement preparedStatement = myConn.prepareStatement(sql);
           preparedStatement.setString(1, encUsername);
           preparedStatement.setString(2, encPassword);
         
          
           myRs = preparedStatement.executeQuery();
           
           int count = 0;
           while(myRs.next()){
              
               count++;
           }
           if(count == 1){
               this.loggedIn = true;
               System.out.println("Successfully verified");
               myConn = DriverManager.getConnection(AccessControl.inventoryDatabase,dbEmp, dbPass);
           }else{
               this.loggedIn = false;
               System.out.println("Verify failed, please insert a valid password");
           }
           
         */  
       }catch(SQLException e){
           this.loggedIn = false;
           e.printStackTrace();
          System.out.println("Fatal error");

       }
       
    }
    
    public boolean isLoggedIn() throws SQLException {
       
        return this.loggedIn;
    }
    
    public boolean logOff() throws SQLException {
        myConn.close();
        this.loggedIn = false;
        return this.loggedIn;
    }
    

    


    public void listViews(){
    String sql = "SHOW FULL TABLES IN Inventory WHERE TABLE_TYPE LIKE 'VIEW'";
    
    try{
        PreparedStatement preparedStatement = myConn.prepareStatement(sql); 
        myRs = preparedStatement.executeQuery();
        System.out.println("Available Views");
        while(myRs.next()){
            System.out.println("        " + myRs.getString("Tables_in_Inventory"));
        }
    }catch(SQLException e){
        System.out.println("Views are unavailable");
    }
  }
    
    public void viewData(String input){
    try{
        String sql = "SELECT * FROM "+AccessControl.getViewName(input) ;
        myStmt = myConn.createStatement();
        myRs = myStmt.executeQuery(sql);
        System.out.println("Name:Brand:Year:Stock");
        while(myRs.next()){
            String name = myRs.getString("Plane Name");
            String brand = myRs.getString("Brand");
            int year = myRs.getInt("Year");
            int stock = myRs.getInt("Stock");
            System.out.println(name + ":" + brand +":"+year+":"+stock);
        }
    }catch(SQLException e){
        System.out.println("SELECTING FROM VIEW ERROR");
        e.printStackTrace();
        System.exit(0);
    }
    
    
    }
    
    private void assignKey(){
        
        try{
        Connection keyConn = DriverManager.getConnection(AccessControl.loginDatabase, dbEmp, dbPass);
        String sql = "SELECT key_val FROM roles WHERE role_name = ?";
        PreparedStatement preparedStatement = keyConn.prepareStatement(sql);
        preparedStatement.setString(1,this.role);
        System.out.println(preparedStatement);
        
        ResultSet keyRs = preparedStatement.executeQuery();
        if(keyRs.next()){
            this.key = keyRs.getInt("key_val");
        }
        keyConn.close();
        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("FATAL ERROR");
            
    }
        
       
    }



 
    
  
 
    
}
