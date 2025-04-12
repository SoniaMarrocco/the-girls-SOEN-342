package AuctionSystem;

import DBaccess.AdminDB;
import DBaccess.ExpertDB;
import DBaccess.ViewingDB;
import DBaccess.eventScheduleDB;

import java.sql.SQLException;
import java.util.Scanner;

public class Administrator {
    private String name;
    private static Administrator adminInstance;

    private Administrator(String name) {
        this.name = name;
    }

    public static Administrator getInstance(String name) {
        if (adminInstance == null) {
            adminInstance = new Administrator(name);
        }
        return adminInstance;
    }

    public void createAccount(String name, String email, String password, String affiliation, String contactInfo, String description) {
        Client newClient = new Client(name, email, password, affiliation, contactInfo, description);
        boolean approved = approveClientSignUp(newClient);

        if (approved) {
            System.out.println("Sign up successful!");
        } else {
            System.out.println("Sign up was rejected.");
        }
    }

    public boolean approveClientSignUp(Client client) {
        try {
            if (AdminDB.isEmailRegistered(client.getEmail())) {
                System.out.println("Email is already registered.");
                return false;
            }
            if (client.getPassword().length() < 8) {
                System.out.println("Password must be at least 8 characters long.");
                return false;
            }
            return AdminDB.insertClient(client);
        } catch (Exception e) {
            System.err.println("Error during client sign-up approval: " + e.getMessage());
            return false;
        }
    }

    public void createExpertAccount(String name, String email, String password, String contact, int licenseNum, String specialty) {
        try {
            Expert expert = new Expert(name, email, password, contact, licenseNum, specialty);
            boolean success = ExpertDB.insertExpert(expert);
            if (success) {
                System.out.println("Expert account successfully created.");
            } else {
                System.out.println("Failed to create expert account.");
            }
        } catch (Exception e) {
            System.err.println("Error creating expert account: " + e.getMessage());
        }
    }

    public void createAuctionHouse(String name) throws SQLException {

            AuctionHouse auctionHouse = new AuctionHouse( name);
            AdminDB.insertAuctionHouse(auctionHouse);
            System.out.println("Auction house created.");
    }

    public void createAuction(String specialty, String title, int auctionHouseId) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Which type of auction would you like to create?");
        System.out.println("[1] Normal (In-person) Auction");
        System.out.println("[2] Online Auction");
        System.out.print("Your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (choice == 1) {
            int viewingId = ViewingDB.createViewing(auctionHouseId);
            NormalAuction normalAuction = new NormalAuction(specialty, title);
            AdminDB.insertNormalAuctionWithViewing(normalAuction, auctionHouseId, viewingId); // <-- updated


            System.out.print("Address: "); String address = scanner.nextLine();
            System.out.print("City: "); String city = scanner.nextLine();
            System.out.print("Date: "); String date = scanner.nextLine();
            System.out.print("Start Time: "); String startTime = scanner.nextLine();
            System.out.print("End Time: "); String endTime = scanner.nextLine();
    
            int newAuctionId = eventScheduleDB.getLastInsertedAuctionId();

            if (newAuctionId > 0) {
                // Create the event schedule with the collected information
                boolean scheduleCreated = eventScheduleDB.createEventSchedule(
                    address, city, date, startTime, endTime, newAuctionId);
                
                if (scheduleCreated) {
                    System.out.println("Normal auction and schedule created successfully.");
                } else {
                    System.out.println("Auction created but there was an error creating the schedule.");
                }
            } else {
                System.out.println("Auction created but couldn't retrieve its ID for scheduling.");
            }
        } else if (choice == 2) {
            OnlineAuction onlineAuction = new OnlineAuction(specialty, title);
            AdminDB.insertOnlineAuction(onlineAuction, auctionHouseId); // <-- updated
            System.out.println("Online auction created.");
        } else {
            System.out.println("Invalid selection. No auction created.");
        }
    }

    public void createObject(String name, String description, boolean available, int auctionHouseId, int normalAuctionId, int onlineAuctionId) throws SQLException {

            Objects object = new Objects(name, description, available);
            AdminDB.insertObject(object, auctionHouseId, normalAuctionId, onlineAuctionId);
            System.out.println("Object created.");
    }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


}
