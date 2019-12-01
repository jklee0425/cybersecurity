package finalJar;
import java.sql.Timestamp;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;
import java.util.Date;
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
        return grantString.substring(11,grantString.length());
    }
    
    
    public static void showAllCommands(){
        System.out.println("SHOWING ALL THE COMMANDS");
    }
    
    public static boolean createViewCheck(ArrayList<String> listCol, ArrayList<String> aliasCol){
        //if the list are the same size, then it is correct
        return listCol.size() == aliasCol.size();
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
    public static void logger(String name, String log) {
        Connection keyConn;
        try {
            keyConn = DriverManager.getConnection(AccessControl.loginDatabase, "sampleuser", "CodeHaze1");
            PreparedStatement ps = keyConn.prepareStatement("INSERT into Logs (username, time, log) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setTimestamp(2, new Timestamp(new Date().getTime()));
            ps.setString(3, log);
            ps.executeUpdate();
            keyConn.close();
        }catch(SQLException e){
            System.out.println("logger error");
            e.printStackTrace();
        }
    }

    public static void getLogs() {
        try {
            Connection conn = DriverManager.getConnection(AccessControl.loginDatabase, "sampleuser", "CodeHaze1");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Logs");
            FileWriter fw = new FileWriter(System.getProperty("user.dir") + "\\ABCFS\\logs.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            while (rs.next()) {
                try {
                    for (int i = 1; i <= 3; i++) {
                        bw.write(String.valueOf(rs.getString(i)));
                    }
                    bw.newLine();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            bw.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getLogs(String username) {
        try {
            Connection conn = DriverManager.getConnection(AccessControl.loginDatabase, "sampleuser", "CodeHaze1");
            String sql = "SELECT * FROM Logs WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            FileWriter fw = new FileWriter(System.getProperty("user.dir") + "\\ABCFS\\logs.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            while (rs.next()) {
                try {
                    for (int i = 1; i <= 3; i++) {
                        bw.write(String.valueOf(rs.getString(i)));
                    }
                    bw.newLine();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            bw.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
