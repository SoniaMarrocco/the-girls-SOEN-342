import AuctionSystem.*;
import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static AuctionSystem.Client.client_login;
import static AuctionSystem.Objects.searchObject;
import static AuctionSystem.Objects.selectObject;

public class Main {
    public static void main(String[] args) {
        Client loggedInClient;
        Administrator admin = Administrator.getInstance("Admin");

        Scanner scanner = new Scanner(System.in);
        try {
            // Just initialize the connection through your existing DatabaseManager
            // This sets up the connection for the entire application
            DatabaseManager.getConnection();
            System.out.println("Connected to the database.");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }

        System.out.println("Welcome to the Auction system!\n ");
        while(true) {
            System.out.println("\nHome Page");
            System.out.print("[1] Signup \n[2] Login \n[3] Enter System as Admin \n[4] Exit System\n");

            int option = scanner.nextInt();

            if (option == 1){
                System.out.println("Please enter all the information below:/n");
                String garbage = scanner.nextLine();
                System.out.println("Name: ");
                String name = scanner.nextLine();
                System.out.println("Email: ");
                String email = scanner.nextLine();
                System.out.println("Password (Must be greater than 8 characters): ");
                String pass = scanner.nextLine();
                System.out.println("Affiliation: ");
                String aff = scanner.nextLine();
                System.out.println("Contact Information: ");
                String contact = scanner.nextLine();
                System.out.println("Description: ");
                String desc = scanner.nextLine();
                admin.createAccount(name, email, pass, aff,contact, desc);

            }

            else if (option == 2) {

                loggedInClient = client_login();

                while(true) {
                    System.out.println("[1]: Search Object");
                    System.out.println("[2]: Search Auction");
                    System.out.println("[3]: Exit");

                    int clientOption = scanner.nextInt();

                    if (clientOption == 1) {
                        searchObject();
                        System.out.print("Please enter the object ID of desired object: ");
                        int objectId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline left-over
                        
                        // Get object from database using the separate method
                        Objects object = selectObject(objectId);
                        
                        if (object != null) {
                            // Display object information
                            System.out.println("Object Details:");
                            System.out.println("Description: " + object.getDescription());
                            System.out.println("Available: " + (object.getAvailable() ? "Yes" : "No"));
                            
                            // Check if the object is in an auction
                            boolean inAuction = isObjectInAuction(objectId);
                            
                            while (true) {
                                if (inAuction) {
                                    System.out.println("This Object's auction is in person. Would you like to book a viewing?\nEnter 0 for NO and 1 for YES");
                                    int viewingOption = scanner.nextInt();
                                    if (viewingOption == 0) {
                                        break;
                                    }
                                    else if (viewingOption == 1) {
                                        loggedInClient.getViewing().viewingRegistration();
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
                        // Search Auction code
                    }
                    else if (clientOption == 3) {
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
                break;
            }
            else {
                System.out.print("enter a valid input please\n");
            }
        }

        System.out.println("\nThank you for using the Auction System!");
    }
    
    private static boolean isObjectInAuction(int objectId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean inAuction = false;
        
        try {
            conn = DatabaseManager.getConnection();
            
            // Query to check if the normalAuctionID is null
            String query = "SELECT normalAuctionID FROM Objects WHERE objectsId = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, objectId);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Check if normalAuctionID is not null
                Object auctionId = rs.getObject("normalAuctionID");
                inAuction = (auctionId != null);
            }
            
        } catch (SQLException e) {
            System.err.println("Database error while checking auction status: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }
        
        return inAuction;
    }
}