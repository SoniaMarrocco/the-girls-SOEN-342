package DBaccess;

import AuctionSystem.Client;
import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public static int createViewing(int auctionHouseId) {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "INSERT INTO viewing (auctionHouseId) VALUES (?)";
            
            // Use RETURN_GENERATED_KEYS to get the auto-generated viewingId
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, auctionHouseId);
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Failed to create viewing, no rows affected.");
                pstmt.close();
                return -1;
            }
            
            // Get the generated viewing ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            int viewingId = -1;
            if (generatedKeys.next()) {
                viewingId = generatedKeys.getInt(1);
            } else {
                System.out.println("Failed to get the viewingId after insertion.");
            }
            
            generatedKeys.close();
            pstmt.close();
            return viewingId;
            
        } catch (SQLException e) {
            System.err.println("Error creating viewing: " + e.getMessage());
            return -1;
        }
    }
    
    /**
     * Gets the most recently created viewing ID
     * 
     * @return the last inserted viewing ID or -1 if an error occurred
     */
    public static int getLastInsertedViewingId() {
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT MAX(viewingId) as last_id FROM viewing";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            int lastId = -1;
            if (rs.next()) {
                lastId = rs.getInt("last_id");
            }
            
            rs.close();
            stmt.close();
            return lastId;
            
        } catch (SQLException e) {
            System.out.println("Error retrieving last viewing ID: " + e.getMessage());
            return -1;
        }
    }

    
}
