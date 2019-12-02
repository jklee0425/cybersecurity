import java.util.Date;
import java.sql.Timestamp;
import java.sql.*;
import java.text.*;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ErikD
 */
public class AccessControl {

    private static String[] roleArray = {"SALESPERSON","WAREHOUSE"};
   
    public static String loginDatabase = "jdbc:mysql://34.83.70.56:3306/LoginSchema";
    public static String accountDatabase = "jdbc:mysql://34.83.70.56:3306/Accounts";
    public static String inventoryDatabase = "jdbc:mysql://34.83.70.56:3306/Inventory";

    
    public static String getGrantsUsername(String grantString){
        String substring = grantString.substring(11,grantString.length());
        return substring;
    }

    
    public static void showAllCommands(String role){
        if(role.toUpperCase().equals("WAREHOUSE")){
            System.out.println("listViews");
            System.out.println("viewData");
        }else if(role.toUpperCase().equals("SALESPERSON")){
            System.out.println("listViews");
            System.out.println("viewData");
            System.out.println("sell");
            
        }else if(role.toUpperCase().equals("CUSTODIAN")){
            System.out.println("createUser");
            System.out.println("listSales");
            System.out.println("listWarehouse");
            System.out.println("viewLogs");
            
            
        }
        System.out.println("logout");
    }
    
    public static boolean createViewCheck(ArrayList<String> listCol, ArrayList<String> aliasCol){
        //if the list are the same size, then it is correct
        if(listCol.size() == aliasCol.size()){
            return true;
        }
        return false;
    }
    
    public static int returnRoleID(String rolename){
        
       
      
        if(rolename.equals("WAREHOUSE")){
            
            return 100;
        } else if(rolename.equals("SALESPERSON")){
            return 101;
        }
        return -1;
    }
    
    public static int getRoleKey(String rolename){
        Connection keyConn;
        try{
        keyConn = DriverManager.getConnection(AccessControl.loginDatabase, "sampleuser", "CodeHaze1");
        String sql = "SELECT key_val FROM roles WHERE role_name = ?";
        PreparedStatement preparedStatement = keyConn.prepareStatement(sql);
        preparedStatement.setString(1,rolename);
        ResultSet myRs = preparedStatement.executeQuery();
       
        int id = -1;
        while(myRs.next()){
            id = myRs.getInt("key_val");
        }
        if(id == -1){
            System.out.println("Login Server is down");
        }
         keyConn.close();
        return id;
        }catch(SQLException e){
            System.out.println("Failed to get roles");
            e.printStackTrace();
            System.exit(0);
        }
        
       
        
        return -1;
    }
    public static boolean validRole(String rolename){
        String formated = rolename.toUpperCase();
        for(int i = 0; i < roleArray.length;i++){
            if(roleArray[i].equals(formated)){
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAllowed(String rolename, String branch){
       
        Connection keyConn;
        try{
        keyConn = DriverManager.getConnection(AccessControl.loginDatabase, "sampleuser", "CodeHaze1");
        String sql = "SELECT now()";
        PreparedStatement preparedStatement = keyConn.prepareStatement(sql);
        ResultSet myRs = preparedStatement.executeQuery();
        String time = "";
        while(myRs.next()){
            //System.out.println("RUN");
            time = (String)myRs.getString("now()").substring(11,13);
        }
          int intTime = Integer.parseInt(time);
        if(rolename.toUpperCase() == "WAREHOUSE"){
            
              return true;
        }else if(rolename.toUpperCase() == "SALESPERSON"){
            //open from 8 to 5;
            int lowerBound = 0;
            int upperBound = 0;
            if(branch.toUpperCase().equals("VANCOUVER")){
                lowerBound = 8;
                upperBound = 17; 
            if(intTime > lowerBound && intTime < upperBound){
                keyConn.close();
                return true;
            }else{
                keyConn.close();
                return false;
            }
            }else if(branch.toUpperCase().equals("GERMANY")){
               
             //11pm in van is 8 am ger
            
             //8 am in van is 5 pm ger
             //between 11 and 8
             //between 23 and 08
             lowerBound = 23;
             upperBound = 8;
             //if the time is between 23 and 08, then they are open
             //if the number is less than 23, can't access
             //if the number is greater than 08, can't access
             
             if(intTime > lowerBound || intTime < upperBound){
                 keyConn.close();
                 return true;
             }else{
                keyConn.close();
                 return false;
             }
             
             
                
            } else if(branch.replaceAll(" ","_").toUpperCase().equals("NEW_YORK")){
                
                //8 am in van is 11am in new york
                //5pm in van is 8pm in new york
                //5am in van is 8 am in newyork
                //2pm in van is 5pm in newyork
                lowerBound = 5;
                upperBound = 14;
                if(intTime < lowerBound && intTime > upperBound){
                    keyConn.close();
                    return true;
                }else{
                    keyConn.close();
                    return false;
                }
                
            }
            

            
        }
        }catch(SQLException e){
            System.out.println("Failed to get roles");
            e.printStackTrace();
            System.exit(0);
        }
 
        
        return false;
    }
    
    public static boolean validBranch(String branchName){
        String formattedString = branchName.replaceAll(" ", "_").toUpperCase();
        if(formattedString.equals("VANCOUVER") || formattedString.equals("NEW_YORK")|| formattedString.equals("GERMANY")){
            return true;
        }
        return false;
    }
    
    public static void generateLog(String name, String action){
    Connection keyConn;
    try{
        keyConn = DriverManager.getConnection(AccessControl.loginDatabase,"sampleuser","CodeHaze1");
        String sql = "INSERT into Logs(username,action,date) VALUES (?,?,?)";
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        PreparedStatement statement = keyConn.prepareStatement(sql);
        statement.setString(1,name);
        statement.setString(2, action);
        statement.setString(3,timeStamp);
        statement.executeUpdate();
        keyConn.close();
    }catch(SQLException e){
        e.printStackTrace();
        System.out.println("Log error");
    }
    }
 
}
