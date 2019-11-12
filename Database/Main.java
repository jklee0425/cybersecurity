import java.sql.*;
import java.util.Scanner;
import java.io.Console;
public class Main {
    
 public static void main(String[] args) throws SQLException{
     
     Scanner input = new Scanner(System.in);
     String command = "empty";
     do{
         System.out.println("Please input mode. Warehouse, Custodian, or Salesperson");
         command = input.next();
         
         //Warehouse commands
         
         
         
         
         if(command.equals("Warehouse")){
             //Get user info to input
             System.out.println("Please enter warehouse account");
             String username = input.next();
             System.out.println("Please enter warehouse password");
             String password = input.next();
             //Create the warehouse object and then log them in
             Warehouse currentUser = new Warehouse(username,password);
             currentUser.login();
             //if the log in is succcessful show the commands
             while(currentUser.isLoggedIn()){
                 System.out.println();
                 System.out.println("Please enter a command for warehouse");
                 command = input.next();
                 if(command.equals("logout")){
                     currentUser.logOff();
                     System.out.println("Logging Off");
                 }else if(command.equals("showcommand")){
                     AccessControl.showAllCommands();
                 }else if(command.equals("isLoggedIn")){
                     System.out.println(currentUser.isLoggedIn());
                 }else{
                     System.out.println("Unknown command entered");
                 }
             }
             
             
             //Custodian commands
             
             
             
             
         }else if(command.equals("Custodian")){
             System.out.println("Please enter custodian username");
             String username = input.next();
             
             System.out.println("Please enter custodian password");
             String password = input.next();
             
             Custodian currentUser = new Custodian(username, password);
             currentUser.login();
             while(currentUser.isLoggedIn()){
                 System.out.println("Enter command");
                 command = input.next();
                 if(command.equals("logout")){
                     currentUser.logOff();
                     System.out.println("Logging Out");
                 }else if(command.equals("showUsers")){
                  currentUser.showUsers();
                 }else if(command.equals("createView")){
                     System.out.println("Enter table name to create view");
                     String tableName = input.next();
                     
                     System.out.println("Enter view name");
                     String viewName = input.next();
                     String colName = "";
                     do{
                     System.out.println("Enter col_names, 0 to terminate");
                     colName = input.next();
                     }while(!colName.equals("0"));
                 }
                 else{
                     System.out.println("Invalid input");
                 }
             
             }
         
         }
     }while(!command.equals("exit"));
     /*
     Custodian testCust = new Custodian("root_account","root_account");
     Warehouse testWarehouse = new Warehouse("Erik","root");
     System.out.println(testWarehouse.login());
     testCust.login();
     //System.out.println(testWarehouse.login());
     
     
     
     //System.out.println(testCust.login());
     //testCust.showUsers();
     testCust.showGrants("root_account");
     //testCust.createView();
    
     */
    }

}
