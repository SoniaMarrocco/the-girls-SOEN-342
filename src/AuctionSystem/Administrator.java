package AuctionSystem;

import DBaccess.AdminDB;
import DBaccess.ExpertDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class Administrator {
    private String name;
    private ArrayList<Client> clients;
    private static Administrator adminInstance;

    private Administrator(String name) {
        this.name = name;
        this.clients = new ArrayList<>();
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
            clients.add(newClient);
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

            Auction auction = new Auction(specialty, title);
            AdminDB.insertAuction(auction, auctionHouseId);
            System.out.println("Auction created.");
    }

    public void createObject(String name, String description, boolean available, int auctionHouseId, Integer normalAuctionId, Integer onlineAuctionId) throws SQLException {

            Objects object = new Objects(name, description, available);
            AdminDB.insertObject(object, auctionHouseId, normalAuctionId, onlineAuctionId);
            System.out.println("Object created.");
    }


    // --- Getters & Setters ---

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ArrayList<Client> getClients() { return clients; }
    public void setClients(ArrayList<Client> clients) { this.clients = clients; }
}
