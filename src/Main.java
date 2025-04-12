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
        Expert loggedInExpert;
        Administrator admin = Administrator.getInstance("Admin");
        Service service;

        Scanner scanner = new Scanner(System.in);
        try {
            DatabaseManager.getConnection();
            System.out.println("Connected to the database.");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }

        System.out.println("\n==== Welcome to the Auction System ====");
        while(true) {
            System.out.println("\n====Home Page====");
            System.out.print("[1] Signup \n[2] Client Login \n[3] Expert Login\n[4] Enter System as Admin \n[5] Exit System\n");
            System.out.println("Choose an option: ");
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
                    System.out.print("Password: ");String password = scanner.nextLine();
                    System.out.print("Contact Info: ");String contact = scanner.nextLine();
                    System.out.print("Specialty: ");String specialty = scanner.nextLine();
                    System.out.print("License Number: ");int licenseNum = scanner.nextInt();
                    scanner.nextLine();

                    admin.createExpertAccount(name, email, password, contact, licenseNum, specialty);
                }
                else {
                    System.out.println("Invalid signup option.");
                }
            }


            else if (option == 2) {

                loggedInClient = login();

                while(true) {
                    System.out.println("====Client Menu====");
                    System.out.println("[1]: Search Object");
                    System.out.println("[2]: Search Auction");
                    System.out.println("[3]: Request a Service");
                    System.out.println("[4]: Logout");
                    System.out.print("Choose one: ");

                    int clientOption = scanner.nextInt();

                    if (clientOption == 1) {
                        Objects.searchObject();
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
                        Auction.searchAuction();
                        System.out.print("Please enter the Auction ID of desired Auction: ");
                        int auctionId = scanner.nextInt();
                        

                        Auction auction = loggedInClient.getAuction().selectAuction(auctionId); 
                    } 
                    else if (clientOption == 3) {
                        System.out.println("What type of service would you like to book?");
                        System.out.println("[1]: Object Consulting\n[2]: Object Advising\n[3]: Auction Attendance\n");
                        int serviceType = scanner.nextInt();
                        scanner.nextLine();
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
                        while(true) {
                            System.out.print("Would you like to request a service? (Y/N): ");
                            String choice = scanner.nextLine().trim();

                            if(choice.equalsIgnoreCase("Y")){
                                System.out.println("Which service from the list above?: ");
                                int serviceChoice = scanner.nextInt();
                                scanner.nextLine();
                                service.clientBookServiceTime(loggedInClient,serviceChoice );

                                System.out.print("You have successfully requested this service, press any key to continue...");
                                scanner.nextLine();
                                break;
                            }
                            else if (choice.equalsIgnoreCase("N")){break;}
                            else {System.out.println(" Invalid Input, try again...\n");}
                        }

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
                loggedInExpert = Expert.login();
                while(true) {
                    System.out.println("====Expert Menu====");
                    System.out.println("[1]: Create Service (offering availability)");
                    System.out.println("[2]: View Objects");
                    System.out.println("[3]: View Auctions");
                    System.out.println("[4]: Logout");
                    System.out.print("Choose one: ");
                    int expertOption = scanner.nextInt();
                    scanner.nextLine();

                    if (expertOption == 1){
                        loggedInExpert.createService();
                    }
                    else if (expertOption == 2){
                        Objects.searchObject();
                        System.out.print("\nPress Enter to continue...");
                        scanner.nextLine();
                    }
                    else if (expertOption == 3) {
                        Auction.searchAuction();
                        System.out.print("\nPress Enter to continue...");
                        scanner.nextLine();
                        scanner.nextLine();

                    }
                    else if (expertOption == 4){ break;}
                    else {System.out.println("Invalid option try again");}


                }
            }
            else if (option == 4) {
                while (true) {
                    System.out.println("\n==== Admin Menu ====");
                    System.out.println("[1] Create Expert Account");
                    System.out.println("[2] Create Object");
                    System.out.println("[3] Create Auction");
                    System.out.println("[4] Create Auction House");
                    System.out.println("[5] Exit");
                    System.out.print("Choose an option: ");

                    int adminOption = scanner.nextInt();
                    scanner.nextLine();

                    if (adminOption == 1) {
                        System.out.print("Name: "); String name = scanner.nextLine();
                        System.out.print("Email: "); String email = scanner.nextLine();
                        System.out.print("Password: "); String password = scanner.nextLine();
                        System.out.print("Contact Info: "); String contact = scanner.nextLine();
                        System.out.print("Specialty: "); String specialty = scanner.nextLine();
                        System.out.print("License Number: "); int licenseNum = scanner.nextInt();
                        scanner.nextLine();

                        admin.createExpertAccount(name, email, password, contact, licenseNum, specialty);
                    }

                    else if (adminOption == 2) {
                        System.out.print("Object Name: "); String objName = scanner.nextLine();
                        System.out.print("Description: "); String objDesc = scanner.nextLine();
                        System.out.print("Available (true/false): "); boolean available = scanner.nextBoolean();
                        scanner.nextLine();

                        try {
                            AdminDB.getAllAuctionHouses();
                            System.out.print("Select Auction House ID to associate: "); int houseId = scanner.nextInt();
                            scanner.nextLine();

                            System.out.println("\nAssociate this object with:");
                            System.out.println("[1] Normal (In-person) Auction");
                            System.out.println("[2] Online Auction");
                            System.out.println("[3] None");
                            System.out.print("Your choice: ");
                            int associationChoice = scanner.nextInt();
                            scanner.nextLine();

                            Integer normalId = null;
                            Integer onlineId = null;

                            if (associationChoice == 1) {
                                AdminDB.getAllNormalAuctions();
                                System.out.print("Enter the Normal Auction ID to associate: ");
                                normalId = scanner.nextInt();
                                scanner.nextLine();
                            } else if (associationChoice == 2) {
                                AdminDB.getAllOnlineAuctions();
                                System.out.print("Enter the Online Auction ID to associate: ");
                                onlineId = scanner.nextInt();
                                scanner.nextLine();
                            } else if (associationChoice == 3) {
                                System.out.println("Object will not be associated with any auction.");
                            } else {
                                System.out.println("Invalid option. No auction association will be made.");
                            }

                            admin.createObject(objName, objDesc, available, houseId, normalId, onlineId);
                        } catch (Exception e) {
                            System.err.println("Error during object creation: " + e.getMessage());
                        }
                    }

                    else if (adminOption == 3) {
                        System.out.print("Auction Title: "); String title = scanner.nextLine();
                        System.out.print("Specialty: "); String specialty = scanner.nextLine();
                        AdminDB.getAllAuctionHouses();
                        System.out.print("Select Auction House ID to associate with this auction: ");
                        int auctionHouseId = scanner.nextInt();
                        scanner.nextLine();

                        admin.createAuction(specialty, title, auctionHouseId);
                    }

                    else if (adminOption == 4) {
                        System.out.print("Enter name of the new Auction House: ");
                        String houseName = scanner.nextLine();

                        admin.createAuctionHouse(houseName);
                    }

                    else if (adminOption == 5) {
                        System.out.println("Returning to main menu...\n");
                        break;
                    }

                    else {
                        System.out.println("Invalid input. Please choose a valid option.");
                    }
                }
            }

            else if (option == 5){
                break;
            }
            else {
                System.out.print("enter a valid input please\n");
            }
        }

        System.out.println("\n\nThank you for using the Auction System!\n");
    }
    

}