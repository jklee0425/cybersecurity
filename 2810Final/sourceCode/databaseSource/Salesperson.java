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
    private String username;
    public Salesperson(String username, String password){
        this.dbEmp = "sales_agent";
        this.dbPass = "agent_sales";
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
               AccessControl.generateLog(username, "Login DB Success");
               this.username = username;
           }else{
               this.loggedIn = false;
               System.out.println("Verify failed, please insert a valid password");
               AccessControl.generateLog(username, "Login DB Failed");
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
       if(input.toUpperCase().equals("GERMANY_VIEW") || input.toUpperCase().equals("NEW_YORK_VIEW") || input.toUpperCase().equals("VANCOUVER_VIEW")){
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
    
    
    
    }








    
     public void sell(String viewName,String name, String brand, int number){
         try{
             String formatted = viewName.toUpperCase();
             int difference = 0;
              String selectSQL = "";
             String tableName = "";
            if(formatted.equals("GERMANY_VIEW")){
                
                tableName = "Germany_Warehouse";
            }else if(formatted.equals("VANCOUVER_VIEW")){
                tableName = "Vancouver_Warehouse";
            }else if(formatted.equals("NEW_YORK_VIEW")){
                tableName = "New_York_Warehouse";
            }
            //First get the plane_id
            selectSQL = "SELECT plane_id FROM Planes WHERE plane_name = ? AND plane_brand = ?";
            PreparedStatement preparedStatement = myConn.prepareStatement(selectSQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2,brand);
            myRs = preparedStatement.executeQuery();
            int planeId = -1;
            while(myRs.next()){
                planeId = myRs.getInt("plane_id");
            }
            if(planeId == -1){
                System.out.println("Error in query");
               
            }else{
                
                //calculate the stock difference
                selectSQL = "SELECT stock FROM "+tableName+" WHERE plane_id = ?";
                preparedStatement = myConn.prepareStatement(selectSQL);
                preparedStatement.setInt(1,planeId);
                myRs = preparedStatement.executeQuery();
                int stock = -1;
                while(myRs.next()){
                    stock = myRs.getInt("stock");
                }if(stock != -1){
                     difference = stock - number;
                    
                     if(difference > 0){
                         selectSQL = "UPDATE " + tableName + " SET stock = ? WHERE plane_id = ?";
                         preparedStatement = myConn.prepareStatement(selectSQL);
                         preparedStatement.setInt(1,difference);
                         preparedStatement.setInt(2,planeId);
                        
                         preparedStatement.executeUpdate();
                         AccessControl.generateLog(username, "Sell update sucessful");
                     }else{
                         System.out.print("Can't sell that many");
                     }
                }else{
                    System.out.println("Invalid stock");
                }
            
            
            }
            
      

           
            
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
