import java.sql.*;
import java.util.Scanner;
import java.io.Console;
import java.util.ArrayList;
import javax.swing.JOptionPane;
public class Main {
    
 public static void main(String[] args) throws SQLException{
    
    
     Scanner input = new Scanner(System.in);
     String command = "empty";
     do{
         command = "exit";
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
                 }else if(command.equals("showCommands")){
                     AccessControl.showAllCommands("Warehouse");
                 }else if(command.equals("isLoggedIn")){
                     System.out.println(currentUser.isLoggedIn());
                 }else if(command.equals("listViews")){
                     currentUser.listViews();
                 }else if(command.equals("viewData")){
                     System.out.println("Which view would you like to see? Enter view name");
                     currentUser.listViews();
                     String viewName = input.next();
                     currentUser.viewData(viewName);
                 }else if(command.equals("showCommands")){
                     AccessControl.showAllCommands("WAREHOUSE");
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
                    System.out.println("Please enter a command, enter showCommand to view commands");
                    command = input.next();
                    if(command.equals("create")){
                        System.out.println("Enter first name");
                        String fName = input.next();
                        System.out.println("Enter last name");
                        String lName = input.next();
                        System.out.println("Enter user name");
                        String userName = input.next();
                        System.out.println("Enter password");
                        String userPass = input.next();
                        System.out.println("Enter role");
                        String roleStr = input.next();
                        System.out.println("Enter branch");
                        String branch = input.next();
                        
                        
                        
                        if(AccessControl.validRole(roleStr)){
                            currentUser.createUser(fName, lName, userName, userPass, roleStr,branch);
                        }else{
                            System.out.println("INVALID ROLE");
                        }
                        
                    
                    }else if(command.equals("listSales")){
                        currentUser.listUsersByRole("SALESPERSON");
                    }else if(command.equals("listWarehouse")){
                        currentUser.listUsersByRole("WAREHOUSE");
                    }else if(command.equals("logout")){
                        currentUser.logOff();
                    }else if(command.equals("viewLogs")){
                        currentUser.viewLogs();
                    }else if(command.equals("showCommands")){
                        AccessControl.showAllCommands("CUSTODIAN");
                    }else{
                        System.out.println("Invalid Command");
                    }
             }
         
         }else if(formattedCom.equals("SALESPERSON")){
             System.out.println("Username");
             String username = input.next();
             System.out.println("Password");
             String password= input.next();
             Salesperson currentUser = new Salesperson(username,password);
             while(currentUser.isLoggedIn()){
                 System.out.println("Please enter a command");
                 command = input.next();
                 if(command.equals("logout")){
                     currentUser.logOff();
                 }else if(command.equals("viewData")){
                     System.out.println("Enter view data");
                     currentUser.listViews();
                     String viewName = input.next();
                     currentUser.viewData(viewName);
                 }else if(command.equals("sell")){
                     System.out.println("Enter view name");
                     String viewName = input.next();
                     System.out.println("Enter Plane name");
                     String planeName = input.next();
                     System.out.println("Enter Brand name");
                     String brand = input.next();
                     System.out.println("Enter how many to sell");
                     int stock = input.nextInt();
                     currentUser.sell(viewName, planeName, brand, stock);
                             
                             
                 }else if(command.equals("listViews")){
                     currentUser.listViews();
                 } else if(command.equals("showCommands")){
                     AccessControl.showAllCommands("SALESPERSON");
                 }
             }
         }
     }while(!command.equals("exit"));
   
   
     
    
    }
 

}


