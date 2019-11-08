
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ErikD
 */
public interface UserInterface {
    //difference between an salesperson, admin, warehouse.
    public String login() throws SQLException;
    public boolean createUser(String newUserName, String newUserPassword, int role) throws SQLException;
    public String showUsers() throws SQLException;
    public String showGrants(String username) throws SQLException;
    public String grantUser(String username, int role) throws SQLException;
    public String createView() throws SQLException;
    public String getTableAll() throws SQLException;
    
    
}
