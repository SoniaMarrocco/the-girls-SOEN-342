package AuctionSystem;

import DBaccess.ClientDB;

import java.util.Scanner;

public class Client {
    private String name;
    private String email;
    private String password;
    private String affiliation;
    private String contactInfo;
    private String description;

    private Auction auction = new Auction();
    private Objects objects = new Objects();

    private static Client loggedInClient;

    // --- Constructor ---
    public Client(String name, String email, String password, String affiliation, String contactInfo, String description) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.affiliation = affiliation;
        this.contactInfo = contactInfo;
        this.description = description;
    }

    // --- Login Method ---
    public static Client login() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            System.out.print("Enter your password: ");
            String pass = scanner.nextLine();

            Client client = verifyCredentials(email, pass);

            if (client != null) {
                loggedInClient = client;
                System.out.println("Login successful! Welcome, " + client.getName());
                break;
            } else {
                System.out.println("Invalid email or password. Please try again.");
            }
        }

        return loggedInClient;
    }

    // Uses DAO to verify credentials
    private static Client verifyCredentials(String email, String password) {
        try {
            return ClientDB.getClientByCredentials(email, password);
        } catch (Exception e) {
            System.err.println("Database error during login: " + e.getMessage());
            return null;
        }
    }

    public static Client getLoggedInClient() {
        return loggedInClient;
    }

    // --- Getters and Setters ---

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Objects getObjects() {
        return objects;
    }

    public void setObjects(Objects object) {
        this.objects = object;
    }
}
