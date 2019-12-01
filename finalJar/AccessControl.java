package finalJar;
import java.sql.*;
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
    
    
    public static void showAllCommands(){
        System.out.println("SHOWING ALL THE COMMANDS");
    }
    
    public static boolean createViewCheck(ArrayList<String> listCol, ArrayList<String> aliasCol){
        //if the list are the same size, then it is correct
        if(listCol.size() == aliasCol.size()){
            return true;
        }
        return false;
    }
    
    public static int getRoleID(String rolename){
        if(rolename.equals("WAREHOUSE")){
            return 100;
        } else if(rolename.equals("SALESPERSON")){
            return 101;
        }
        return -1;
    }

    public static String getRoleName(int roleID){
        return roleID == 100 ? roleArray[0] : roleID == 101 ? roleArray[1] : "";
    }

    public static String getBranchName(String userName) {
        // TODO
        Connection keyConn;
        String retVal = "";
        try{
            keyConn = DriverManager.getConnection(AccessControl.loginDatabase, "sampleuser", "CodeHaze1");
            String sql = "SELECT branch FROM user WHERE username = ?";
            PreparedStatement preparedStatement = keyConn.prepareStatement(sql);
            preparedStatement.setString(1, userName);
            ResultSet myRs = preparedStatement.executeQuery();
            
            while(myRs.next()){
                retVal = myRs.getString("branch");
            }
            if(retVal.equals("")){
                System.out.println("Login Server is down");
            }
            keyConn.close();
            return retVal;
        }catch(SQLException e){
            System.out.println("Failed to get roles");
            e.printStackTrace();
            System.exit(0);
        }
        return retVal;
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
 
}
