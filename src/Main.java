import AuctionSystem.*;
import DBaccess.*;
import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static AuctionSystem.Client.login;
import static DBaccess.ObjectsDB.getViewingFromObject;

public class Main {
    public static void main(String[] args) throws SQLException {
        Client loggedInClient;
        Administrator admin = Administrator.getInstance("Admin");
        Service service;

        Scanner scanner = new Scanner(System.in);
        try {
            DatabaseManager.getConnection();
            System.out.println("Connected to the database.");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }

        System.out.println("=== Welcome to the Auction System ===");
        while(true) {
            System.out.println("\nHome Page");
            System.out.print("[1] Signup \n[2] Client Login \n[3] Expert Login\n[4] Enter System as Admin \n[5] Exit System\n");

            int option = scanner.nextInt();

            if (option == 1) {
                System.out.println("\n[1] Sign up as Client\n[2] Sign up as Expert\n");
                int signupChoice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (signupChoice == 1) {
                    System.out.println("\n--- Client Signup ---");
                    System.out.print("Name: ");String name = scanner.nextLine();
                    System.out.print("Email: "); String email = scanner.nextLine();
                    System.out.print("Password (min 8 characters): "); String password = scanner.nextLine();
                    System.out.print("Affiliation: ");String affiliation = scanner.nextLine();
                    System.out.print("Contact Info: ");String contact = scanner.nextLine();
                    System.out.print("Description: ");String description = scanner.nextLine();

                    admin.createAccount(name, email, password, affiliation, contact, description);
                }

                else if (signupChoice == 2) {
                    System.out.println("\n--- Expert Signup ---");
                    System.out.print("Name: ");String name = scanner.nextLine();
                    System.out.print("Email: ");String email = scanner.nextLine();
                    System.out.print("Username: ");String username = scanner.nextLine();
                    System.out.print("Password: ");String password = scanner.nextLine();
                    System.out.print("Contact Info: ");String contact = scanner.nextLine();
                    System.out.print("License Number: ");int licenseNum = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    admin.createExpertAccount(name, email, username, password, contact, licenseNum);
                }
                else {
                    System.out.println("Invalid signup option.");
                }
            }


            else if (option == 2) {

                loggedInClient = login();

                while(true) {
                    System.out.println("[1]: Search Object");
                    System.out.println("[2]: Search Auction");
                    System.out.println("[3]: Request a Service");
                    System.out.println("[4]: Logout");

                    int clientOption = scanner.nextInt();

                    if (clientOption == 1) {
                        loggedInClient.getObjects().searchObject();
                        System.out.print("Please enter the object ID of desired object: ");
                        int objectId = scanner.nextInt();
                        scanner.nextLine();
                        
                        Objects object =  loggedInClient.getObjects().selectObject(objectId);
                        
                        if (object != null) {
                            // Display object information
                            System.out.println("Object Details:");
                            System.out.println("Name: " + object.getName());
                            System.out.println("Description: " + object.getDescription());
                            System.out.println("Available: " + (object.getAvailable() ? "Yes" : "No"));
                            
                            // Check if the object is in an auction
                            boolean inAuction = ObjectsDB.isObjectInAuction(objectId);
                            
                            while (true) {
                                if (inAuction) {
                                    System.out.println("This Object's auction is in person. Would you like to book a viewing?\nEnter 0 for NO and 1 for YES");
                                    int viewingOption = scanner.nextInt();
                                    if (viewingOption == 0) {
                                        break;
                                    }
                                    else if (viewingOption == 1) {
                                        Viewing viewing = getViewingFromObject(objectId);
                                        if (viewing != null) {
                                            viewing.viewingRegistration(loggedInClient);
                                        } else {
                                            System.out.println("No viewing found for this object.");
                                        }
                                        System.out.println ("Successfully signed up for auction viewing");
                                        break;
                                    }
                                    else {
                                        System.out.print("Invalid entry. Please try again");
                                    }
                                } else {
                                    // If not in auction, exit the loop
                                    break;
                                }
                            }
                        }
                    }
                    else if (clientOption == 2) {
                        loggedInClient.getAuction().searchAuction();
                        System.out.print("Please enter the Auction ID of desired Auction: ");
                        int auctionId = scanner.nextInt();
                        

                        Auction auction = loggedInClient.getAuction().selectAuction(auctionId); 
                    } 
                    else if (clientOption == 3) {
                        System.out.println("What type of service would you like to book?");
                        System.out.println("[1]: Object Consulting\n[2]: Object Advising\n[3]: Auction Attendance\n");
                        int serviceType = scanner.nextInt();
                        
                        while (true) {
                            if (serviceType == 1) {
                                service = new Consulting();
                                break;
                            }
                            else if (serviceType == 2) {
                                service = new ObjectAdvising();
                                break;

                            }
                            else if (serviceType == 3) {
                                service = new AuctionAttendance();
                                break;

                            }
                            else {
                                System.out.println("Invalid option. Please try again.");

                            }
                        }
                        service.requestService();
                        
                    }                  
                    else if (clientOption == 4) {
                        break;
                    }
                    else {
                        System.out.println("Invalid option. Please try again.");
                    }
                }
            }
            else if (option == 3) {
                System.out.println("3");
            }
            else if (option == 4){
                System.out.println("3");

            }
            else if (option == 5){
                break;
            }
            else {
                System.out.print("enter a valid input please\n");
            }
        }

        System.out.println("\nThank you for using the Auction System!");
    }
    

}