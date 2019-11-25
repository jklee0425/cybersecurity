import java.sql.*;
import java.util.Scanner;
import java.io.Console;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class Main {
    
 public static void main(String[] args) throws SQLException{
     //Custodian testCust = new Custodian("sampleuser","CodeHaze1");
     //testCust.createUser("Erik","Dengler","warehouseOne","warehouseP","WAREHOUSE");
     Warehouse test = new Warehouse("warehouseOne","warehouseP");
     Scanner input = new Scanner(System.in);
     String command = "empty";
     do{
         System.out.println("Please input mode. Warehouse, Custodian, or Salesperson");
         command = input.next();
         String formattedCom = command.toUpperCase();
         
         //Warehouse commands
         if(formattedCom.equals("WAREHOUSE")){
             //Get user info to input
             System.out.println("Please enter warehouse account");
             String username = input.next();
             System.out.println("Please enter warehouse password");
             String password = input.next();
             //Create the warehouse object and then log them in
             Warehouse currentUser = new Warehouse(username,password);         
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
                 }else if(command.equals("seedata")){
                     currentUser.listViews();
                     
                 }else if(command.equals("viewdata")){
                     System.out.println("Which view would you like to see? Enter view name");
                     currentUser.listViews();
                     command = input.next();
                     String viewName = AccessControl.getViewName(command);
                     currentUser.viewData(viewName);
                 }else{
                     System.out.println("Unknown command entered");
                 }
             }
             
             
             //Custodian commands
         }else if(formattedCom.equals("CUSTODIAN")){
             System.out.println("Please enter custodian username");
             String username = input.next();
             System.out.println("Please enter custodian password");
             String password = input.next();
             Custodian currentUser = new Custodian(username, password);
             while(currentUser.isLoggedIn()){
                    
             }
         
         }
     }while(!command.equals("exit"));
   
   
     
    
    }
 

}


