import java.sql.*;
import java.util.ArrayList;

public class Salesperson {
    private String role = "SALESPERSON";
    private int key;
    private boolean loggedIn;
    private Connection myConn = null;
    private PreparedStatement myStmt = null;
    private ResultSet myRs = null;
    
    private static String dbEmp;
    private static String dbPass;
    
    public Salesperson(String username, String password){
        this.dbEmp = "login_agent";
        this.dbPass = "agent_login@";
        assignKey();
        login(username,password);
        
    }
    
    
     private void login(String username, String password) {
       try{
           String encUsername = AES.encrypt(username,this.key);
           String encPassword = AES.encrypt(password, this.key);
           Connection accountConn = DriverManager.getConnection(AccessControl.accountDatabase,dbEmp, dbPass);
           String sql = "SELECT userName, userPass FROM userInfo WHERE userName = ? AND userPass = ?";
           PreparedStatement preparedStatement = accountConn.prepareStatement(sql);
           preparedStatement.setString(1, encUsername);
           preparedStatement.setString(2, encPassword);
           ResultSet accRs = preparedStatement.executeQuery();
           
           int count = 0;
           while(accRs.next()){
               count++;
           }
           if(count == 1){
               accountConn.close();
               this.loggedIn = true;
               myConn = DriverManager.getConnection(AccessControl.inventoryDatabase,dbEmp,dbPass);
               System.out.println("Successfully verified");
           }else{
               this.loggedIn = false;
               System.out.println("Verify failed, please insert a valid password");
               System.exit(0);
           }
           
           
       }catch(SQLException e){
           this.loggedIn = false;
           e.printStackTrace();
          System.out.println("Fatal error");
       }

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
        System.out.println(input);
    try{
        String sql = "SELECT * FROM "+ input;
      
        Statement viewStmt = myConn.createStatement();
        myRs = viewStmt.executeQuery(sql);
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








    
     public void sell(String viewName,String name, String brand, int number){
         try{
            
            
            String selectSql = "SELECT Stock FROM "+viewName+" WHERE Brand = ? AND 'Plane Name' = ?";
            PreparedStatement preparedStatement = myConn.prepareStatement(selectSql);
            preparedStatement.setString(1,brand);
            preparedStatement.setString(2,name);
            myRs = preparedStatement.executeQuery();
            while(myRs.next()){
                int stock = myRs.getInt("Stock");
                System.out.println("Stock" + stock);
            }
            String sql = "UPDATE "+viewName+" SET STOCK = ? WHERE Brand = ? AND 'Plane Name' = ?";
            //preparedStatement.setInt(1, number);
            //preparedStatement.setString(2,brand);
            //preparedStatement.setString(3,name);
           
            System.out.println(preparedStatement);
         }catch(SQLException e){
             System.out.println("Error in sell");
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
        
    public boolean isLoggedIn() {
        return this.loggedIn;
    }
    public boolean logOff() throws SQLException {
        myConn.close();
        this.loggedIn = false;
        return this.loggedIn;
    }
}
