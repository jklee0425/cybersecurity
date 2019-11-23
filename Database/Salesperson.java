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
        this.dbEmp = "sampleuser";
        this.dbPass = "CodeHaze1";
        assignKey();
        login(username,password);
        displayTable();
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
               System.out.println(accRs.getString("userName"));
               count++;
           }
           if(count == 1){
               accountConn.close();
               this.loggedIn = true;
               System.out.println("Successfully verified");
           }else{
               this.loggedIn = false;
               System.out.println("Verify failed, please insert a valid password");
           }
           
           
       }catch(SQLException e){
           this.loggedIn = false;
           e.printStackTrace();
          System.out.println("Fatal error");
       }

    }
     
     private void displayTable(){
         try{
            myConn = DriverManager.getConnection("jdbc:mysql://35.247.4.229:3306/Inventory", this.dbEmp, this.dbPass);
            String sql = "SELECT * FROM sales_table";
            PreparedStatement preppedStatement = myConn.prepareStatement(sql);
            myRs = preppedStatement.executeQuery();
            System.out.println("Name : Brand : Price : Stock");
            while(myRs.next()){
                System.out.println();
                System.out.print(myRs.getString("Name") + " : ");
                System.out.print(myRs.getString("Brand")+ " : ");
                System.out.print(myRs.getString("Price")+" : ");
                System.out.print(myRs.getString("Stock")+ " : ");
                System.out.println();
                
            }
         }catch(SQLException e){
             e.printStackTrace();
             System.out.println("Database currently unavailable");
         }
         
     }
    
     public void sell(String name, String brand, int number){
         String sql = "SELECT Name, Brand,Stock from sales_table WHERE Name=? AND Brand=?";
         try{
          myStmt = myConn.prepareStatement(sql);
          myStmt.setString(1,name);
          myStmt.setString(2,brand);
          myRs = myStmt.executeQuery();
          int count = -1;
          System.out.println(myStmt);
          while(myRs.next()){
              count = Integer.parseInt(myRs.getString("Stock"));
          }
          if(count < number){
              System.out.println("You cant sell that much");
          }else{
              sql = "UPDATE sales_table SET Stock = ? WHERE Name=? AND Brand=?";
              myStmt = myConn.prepareStatement(sql);
              String strCount = Integer.toString(count - number);
              myStmt.setString(1,strCount);
              myStmt.setString(2,name);
              myStmt.setString(3, brand);
              
              myStmt.executeUpdate();
              displayTable();
          }
          
         }catch(SQLException e){
         e.printStackTrace();
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
