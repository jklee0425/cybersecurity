
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
        
        this.dbEmp = "sampleuser";
        this.dbPass = "CodeHaze1";
        assignKey();
        login(username,password);
        
     
}
    
    
    
    private void login(String username, String password) {
       try{
           String encUsername = AES.encrypt(username,this.key);
           String encPassword = AES.encrypt(password, this.key);
           myConn = DriverManager.getConnection("jdbc:mysql://35.247.4.229:3306/Accounts", dbEmp, dbPass);
           String sql = "SELECT userName, userPass FROM userInfo WHERE userName = ? AND userPass = ?";
           PreparedStatement preparedStatement = myConn.prepareStatement(sql);
           preparedStatement.setString(1, encUsername);
           preparedStatement.setString(2, encPassword);
           myRs = preparedStatement.executeQuery();
           
           int count = 0;
           while(myRs.next()){
               System.out.println(myRs.getString("userName"));
               count++;
           }
           if(count == 1){
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
    
    public boolean isLoggedIn() throws SQLException {
       
        return this.loggedIn;
    }
    
    public boolean logOff() throws SQLException {
        myConn.close();
        this.loggedIn = false;
        return this.loggedIn;
    }
    
    public void listAllInventory() throws SQLException {
        myConn = DriverManager.getConnection("jdbc:mysql://35.247.4.229:3306/Inventory", this.dbEmp, this.dbPass);
        String sql = "SELECT * FROM 'views_test'";
        PreparedStatement preppedStatement = myConn.prepareStatement(sql);
        
    }
    
    //ERROR HERE FIX
    public void listInventoryFromCertainView(String viewName) throws SQLException{
        myConn = DriverManager.getConnection("jdbc:mysql://35.247.4.229:3306/Inventory", this.dbEmp, this.dbPass);
        String sql = "SELECT * FROM ?";
        PreparedStatement preparedStatement = myConn.prepareStatement(sql);
        preparedStatement.setString(1,viewName);
        myRs = preparedStatement.executeQuery();
        
        ResultSetMetaData meta = myRs.getMetaData();
        int count = meta.getColumnCount();
        ArrayList<String> columnNames = new ArrayList<String>();
        for(int i = 0; i < count; i++){
            String col_name = meta.getColumnName(i);
            columnNames.add(col_name);
        }
        
        for(String i : columnNames){
            System.out.println(i);
        }
    }

    public void listViews(){
    String sql = "SHOW FULL TABLES IN Inventory WHERE TABLE_TYPE LIKE 'VIEW'";
    }
    
    private void assignKey(){
        
        try{
        Connection keyConn = DriverManager.getConnection("jdbc:mysql://35.247.4.229:3306/LoginSchema", dbEmp, dbPass);
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
