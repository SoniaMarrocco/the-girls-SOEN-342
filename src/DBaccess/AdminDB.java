package DBaccess;

import AuctionSystem.*;
import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDB {

    public static boolean isEmailRegistered(String email) throws Exception {
        Connection conn = DatabaseManager.getConnection();
        String sql = "SELECT COUNT(*) FROM Client WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();
        boolean exists = rs.next() && rs.getInt(1) > 0;

        rs.close();
        stmt.close();
        return exists;
    }

    public static boolean insertClient(Client client) throws Exception {
        Connection conn = DatabaseManager.getConnection();
        String sql = "INSERT INTO Client (name, email, password, affiliation, contactInfo, description) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, client.getName());
        stmt.setString(2, client.getEmail());
        stmt.setString(3, client.getPassword());
        stmt.setString(4, client.getAffiliation());
        stmt.setString(5, client.getContactInfo());
        stmt.setString(6, client.getDescription());

        int rows = stmt.executeUpdate();
        stmt.close();
        return rows > 0;
    }

    //creating objects, auctionhouses, and auctions
    public static void insertAuctionHouse(AuctionHouse house) throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        String sql = "INSERT INTO AuctionHouse (name) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, house.getName());
        stmt.executeUpdate();
        stmt.close();
    }
    public static void insertNormalAuction(NormalAuction auction, int auctionHouseId) throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        String sql = "INSERT INTO normalAuction (speciality, auctionTitle, auctionHouseId) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, auction.getSpecialty());
        stmt.setString(2, auction.getAuctionTitle());
        stmt.setInt(3, auctionHouseId);
        stmt.executeUpdate();
        stmt.close();
    }

    public static void insertOnlineAuction(OnlineAuction auction, int auctionHouseId) throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        String sql = "INSERT INTO onlineAuction (speciality, auctionTitle, auctionHouseId) VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, auction.getSpecialty());
        stmt.setString(2, auction.getAuctionTitle());
        stmt.setInt(3, auctionHouseId);
        stmt.executeUpdate();
        stmt.close();
    }

    public static void insertObject(Objects object, int auctionHouseId, Integer normalAuctionId, Integer onlineAuctionId) throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        String sql = "INSERT INTO Objects (name, description, available, auctionHouseID, normalAuctionId, onlineAuctionId) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, object.getName());
        stmt.setString(2, object.getDescription());
        stmt.setBoolean(3, object.getAvailable());
        stmt.setInt(4, auctionHouseId);

        if (normalAuctionId != null)
            stmt.setInt(5, normalAuctionId);
        else
            stmt.setNull(5, java.sql.Types.INTEGER);

        if (onlineAuctionId != null)
            stmt.setInt(6, onlineAuctionId);
        else
            stmt.setNull(6, java.sql.Types.INTEGER);

        stmt.executeUpdate();
        stmt.close();
    }


    //getting all from the database...
    public static void getAllAuctionHouses() throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        String sql = "SELECT * FROM AuctionHouse";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        System.out.println("\n--- Available Auction Houses ---");
        while (rs.next()) {
            int id = rs.getInt("auctionHouseId");
            String name = rs.getString("name");
            System.out.println("ID: " + id + " | Name: " + name);
        }
        rs.close();
        stmt.close();
    }

    public static void getAllNormalAuctions() throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        String sql = "SELECT * FROM normalAuction";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        System.out.println("\n--- Available Normal Auctions ---");
        while (rs.next()) {
            int id = rs.getInt("auctionID");
            String title = rs.getString("auctionTitle");
            String specialty = rs.getString("speciality");
            System.out.println("ID: " + id + " | Title: " + title + " | Specialty: " + specialty);
        }
        rs.close();
        stmt.close();
    }

    public static void getAllOnlineAuctions() throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        String sql = "SELECT * FROM onlineAuction";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        System.out.println("\n--- Available Online Auctions ---");
        while (rs.next()) {
            int id = rs.getInt("auctionID");
            String title = rs.getString("auctionTitle");
            String specialty = rs.getString("speciality");
            System.out.println("ID: " + id + " | Title: " + title + " | Specialty: " + specialty);
        }
        rs.close();
        stmt.close();
    }



}
