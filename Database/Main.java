import java.sql.*;
import java.util.Scanner;
import java.io.Console;
public class Main {
    
 public static void main(String[] args) throws SQLException{
     Custodian testCust = new Custodian("root_account","root_account");
     System.out.println(testCust.login());
     testCust.showUsers();
     testCust.showGrants("root_account");
     testCust.createView();
    }

}
