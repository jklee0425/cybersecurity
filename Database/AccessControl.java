package Database;
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

    private static String[] roleArray = {"CUSTODIAN","SALESPERSON","WAREHOUSE"};
    private static boolean[] readWrite = new boolean[2];
    public static String loginDatabase = "jdbc:mysql://35.247.4.229:3306/LoginSchema";
    public static String accountDatabase = "jdbc:mysql://35.247.4.229:3306/Accounts";
    public static String inventoryDatabase = "jdbc:mysql://35.247.4.229:3306/Inventory";
    /*
    public static boolean writePrivelege(String userRole){
        //only TOP_ADMIN role can write to the server
        if(userRole != SUPERUSER){
            return false;
        }
        return true;
    }
    */
    /*
    public static boolean readPrivelege(String userRole){
    //employee from TOP ADMIN to EMPLOYE can read
    if(userRole != SUPERUSER || userRole != EMPLOYEE){
        return false;
    }
    return true;
    }
    */
    public static String showCommands(){
        return "This will be a list of commands that will be set later";
    }
    
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
    
    public static int returnRoleID(String rolename){
        
       
      
        if(rolename.equals("WAREHOUSE")){
            
            return 100;
        } else if(rolename.equals("SALESPERSON")){
            return 101;
        }
        return -1;
    }
    
    public static int getRoleKey(String rolename){
        if(rolename.equals("WAREHOUSE")){
            return 101;
        }else if(rolename.equals("SALESPERSON")){
            return 302;
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
    /*
    public static String getViewName(String input){
        String format = input.toUpperCase();
        
        if(format.equals("GERMANY")){
            return "Germany_View";
        }else if(format.equals("NEW YORK")){
            return "New_York_View";
        } else if(format.equals("VANCOUVER")){
            return "Vancouver_View";
        }
        return "ERORR";
    }
*/
}
