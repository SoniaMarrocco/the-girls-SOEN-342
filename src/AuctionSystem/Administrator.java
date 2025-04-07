package AuctionSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Database.DatabaseManager;

import java.util.ArrayList;

public class Administrator {
    private String name;
    public ArrayList<Client> clients;
    public AuctionHouse[] ah;
    public Expert[] e;
    public static ArrayList<Objects> obj;
    private static Administrator admin;

    private Administrator(String name) {
        this.name = name;
        this.clients = new ArrayList<>();
    }

    public static Administrator getInstance(String name) {
        if (admin == null) {
            // Lazy initialization: only create the instance when it's first needed
            admin = new Administrator(name);
        }
        return admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public ArrayList<Client> setClients(ArrayList<Client> clients) {
        return this.clients = clients;
    }

    public AuctionHouse[] getAh() {
        return ah;
    }

    public void setAh(AuctionHouse[] ah) {
        this.ah = ah;
    }

    public Expert[] getE() {
        return e;
    }

    public void setE(Expert[] e) {
        this.e = e;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(ArrayList<Objects> obj) {
        this.obj = obj;
    }

    public void createAccount(String name, String email, String password, String affiliation, String contact_info, String description) {
        Client client = new Client(name, email, password, affiliation, contact_info, description);
        boolean check = this.approveClientSignUp(client);
        if (check) {
            clients.add(client);
            System.out.print("Sign up successful!");
        }
        else {
            System.out.print("Sign up was rejected");
        }
    }

    public boolean approveClientSignUp(Client client) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseManager.getConnection();

            // Check if email already exists in the database
            String checkSql = "SELECT COUNT(*) FROM Client WHERE email = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setString(1, client.getEmail());
            rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Error: Email already registered.");
                return false;
            }
            rs.close();
            pstmt.close();

            if (client.getPassword().length() < 8) {
                System.out.println("Error: Password must be at least 8 characters long.");
                return false;
            }

            // Insert new client into the database
            String insertSql = "INSERT INTO Client (name, email, password, affiliation, contactInfo, description) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, client.getName());
            pstmt.setString(2, client.getEmail());
            pstmt.setString(3, client.getPassword());
            pstmt.setString(4, client.getAffiliation());
            pstmt.setString(5, client.getContact_info());
            pstmt.setString(6, client.getDescription());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Database error during client sign up: " + e.getMessage());
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                // Note: We don't close the connection as it may be managed externally
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }
    }

    public boolean createAuctionHouse(String name) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseManager.getConnection();

            // Check if auction house with this name already exists
            String checkSql = "SELECT COUNT(*) FROM AuctionHouse WHERE name = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Error: Auction house with this name already exists.");
                rs.close();
                pstmt.close();
                return false;
            }

            rs.close();
            pstmt.close();

            // Insert the new auction house
            String insertSql = "INSERT INTO AuctionHouse (name) VALUES (?)";
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, name);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Auction house '" + name + "' created successfully.");
                return true;
            } else {
                System.out.println("Failed to create auction house.");
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Database error while creating auction house: " + e.getMessage());
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                // Note: We don't close the connection as it may be managed externally
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }
    }


}
