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
    private static String SUPERUSER = "TOP ADMIN";
    private static String EMPLOYEE = "EMPLOYEE";
    private static String WAREHOUSE = "WAREHOUSE";
    private static boolean[] readWrite = new boolean[2];
    public static boolean writePrivelege(String userRole){
        //only TOP_ADMIN role can write to the server
        if(userRole != SUPERUSER){
            return false;
        }
        return true;
    }
    
    public static boolean readPrivelege(String userRole){
    //employee from TOP ADMIN to EMPLOYE can read
    if(userRole != SUPERUSER || userRole != EMPLOYEE){
        return false;
    }
    return true;
    }
    
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
}
