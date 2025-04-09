package DBaccess;

import AuctionSystem.Client;
import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
