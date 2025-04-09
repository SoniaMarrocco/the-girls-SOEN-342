package DBaccess;

import AuctionSystem.Client;
import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientDB {

    public static boolean isEmailRegistered(String email) throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        String query = "SELECT COUNT(*) FROM Client WHERE email = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();
        boolean exists = rs.next() && rs.getInt(1) > 0;

        rs.close();
        stmt.close();
        return exists;
    }

    public static boolean insertClient(Client client) throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        String query = "INSERT INTO Client (name, email, password, affiliation, contactInfo, description) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
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

    public static Client getClientByCredentials(String email, String password) throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        String query = "SELECT * FROM Client WHERE email = ? AND password = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();
        Client client = null;

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

        rs.close();
        stmt.close();
        return client;
    }
}
