
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ErikD
 */
public class Warehouse implements UserInterface {
    private String role;
    private Connection myConn = null;
    private Statement myStmt = null;
    private ResultSet myRs = null;
    @Override
    public String login(String username, String password) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createUser(String username, String newUserPassword,int role) throws SQLException {
        System.out.println("DO NOT HAVE ACCESS TO THIS COMMAND");
        return false;
    }

    @Override
    public String showUsers() throws SQLException {
        System.out.println("DO NOT HAVE ACCESS TO THIS COMMAND");
        return "";
    }

    @Override
    public String showGrants(String username) throws SQLException {
        System.out.println("DO NOT HAVE ACCESS TO THIS COMMAND");
        return "";
    }

    @Override
    public String grantUser(String username) throws SQLException {
        System.out.println("DO NOT HAVE ACCESS TO THIS COMMAND");
        return "";
    }

    @Override
    public String createView() throws SQLException {
        System.out.println("DO NOT HAVE ACCESS TO THIS COMMAND");
        return "";
    }
    
    @Override
    public String getTableAll() throws SQLException{
        return "WAREHOUSE IMPLEMENT SELECT FROM VIEW LATER";
    }
    
}
