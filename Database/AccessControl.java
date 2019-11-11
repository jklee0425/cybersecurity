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

    //only TOP_ADMIN role can write to the server
    public static boolean writePrivelege(String userRole){
        return userRole == SUPERUSER;
    }
    
    //employee from TOP ADMIN to EMPLOYE can read
    public static boolean readPrivelege(String userRole){
        return userRole == SUPERUSER || userRole == EMPLOYEE;
    }
    
    public static String showCommands(){
        return "This will be a list of commands that will be set later";
    }
    
    public static String getGrantsUsername(String grantString){
        return grantString.substring(11,grantString.length());
    }
}
