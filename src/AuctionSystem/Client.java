package AuctionSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import Database.DatabaseManager;

public class Client {
    private String name;
    private String email;
    private String password;
    private String affiliation;
    private String contact_info;
    private String description;

    public Viewing viewing;
    public ArrayList<Objects> obj;
    public ArrayList<AuctionHouse> auction_house;
    public ArrayList<Auction> auction;
    
    // public ArrayList<Services> service = new ArrayList<>();
    private static Client loggedInClient = null;

    public Client(String name, String email, String password, String affiliation, String contact_info, String description) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.affiliation = affiliation;
        this.contact_info = contact_info;
        this.description = description;
        this.viewing = new Viewing();
    }


    //methods

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



    public String getContact_info() {
        return contact_info;
    }



    public void setContact_info(String contact_info) {
        this.contact_info = contact_info;
    }



    public String getDescription() {
        return description;
    }



    public void setDescription(String description) {
        this.description = description;
    }



    public ArrayList<Objects> getObj() {
        return obj;
    }



    public void setObj(ArrayList<Objects> obj) {
        this.obj = obj;
    }



    public ArrayList<AuctionHouse> getAuction_house() {
        return auction_house;
    }



    public void setAuction_house(ArrayList<AuctionHouse> auction_house) {
        this.auction_house = auction_house;
    }



    public ArrayList<Auction> getAuction() {
        return auction;
    }



    public void setAuction(ArrayList<Auction> auction) {
        this.auction = auction;
    }



    public Viewing getViewing() {
        return viewing;
    }



    public void setViewing(Viewing viewing) {
        this.viewing = viewing;
    }



    // public ArrayList<Service> getService() {
    //     return service;
    // }


    // public void setService(ArrayList<Services> service) {
    //     this.service = service;
    // }

    public static Client getLoggedInClient() {
        return loggedInClient;
    }


    public static void setLoggedInClient(Client loggedInClient) {
        Client.loggedInClient = loggedInClient;
    }


    public static Client client_login() {

        Scanner scanner = new Scanner(System.in);

        while(true) {
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String pass = scanner.nextLine();
        try {
            Client client = verifyCredentials(email, pass);

            if (client != null) {
                loggedInClient = client;
                System.out.println("Login successful! Welcome, " + client.getName());
                break;
            } else {
                System.out.println("Invalid email or password.");
            }

        } catch (SQLException e) {
            System.err.println("Database error during login: " + e.getMessage());
        }
    }
        return loggedInClient;
    }

    private static Client verifyCredentials(String email, String password) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Client client = null;

        try {
            Connection conn = DatabaseManager.getConnection();

            // Query to find client by email and password
            String sql = "SELECT * FROM Client WHERE email = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                client = new Client(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("affiliation"),
                        rs.getString("contactInfo"),
                        rs.getString("description")
                );
            }

        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        }

        return client;
    }


}
