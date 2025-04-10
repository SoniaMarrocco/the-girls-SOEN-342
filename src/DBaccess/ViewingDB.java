package DBaccess;

import AuctionSystem.Client;
import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewingDB {

    public static boolean registerClientToViewing(Client client, int viewingId) {
        try {
            Connection conn = DatabaseManager.getConnection();

            // Get clientId from Client object (by email)
            String getClientSql = "SELECT clientId FROM Client WHERE email = ?";
            PreparedStatement getClientStmt = conn.prepareStatement(getClientSql);
            getClientStmt.setString(1, client.getEmail());
            ResultSet rs = getClientStmt.executeQuery();

            int clientId = -1;
            if (rs.next()) {
                clientId = rs.getInt("clientId");
            } else {
                System.out.println("Client not found in the database.");
                rs.close();
                getClientStmt.close();
                return false;
            }

            rs.close();
            getClientStmt.close();

            String checkSql = "SELECT * FROM Client_Viewing WHERE clientId = ? AND viewingId = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, clientId);
            checkStmt.setInt(2, viewingId);

            rs = checkStmt.executeQuery();
            if (rs.next()) {
                System.out.println("Client is already registered for this viewing.");
                rs.close();
                checkStmt.close();
                return false;
            }
            rs.close();
            checkStmt.close();

            String insertSql = "INSERT INTO Client_Viewing (clientId, viewingId) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, clientId);
            insertStmt.setInt(2, viewingId);

            int rows = insertStmt.executeUpdate();
            insertStmt.close();

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Database error during viewing registration: " + e.getMessage());
            return false;
        }
    }
}
