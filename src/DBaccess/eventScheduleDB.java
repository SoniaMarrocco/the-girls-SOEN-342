package DBaccess;

import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class eventScheduleDB {
    
    /**
     * Creates a new event schedule entry with location and timing details
     * 
     * @param address The street address of the event
     * @param city The city where the event will be held
     * @param date The date of the event (format: YYYY-MM-DD)
     * @param startTime The starting time (format: HH:MM)
     * @param endTime The ending time (format: HH:MM)
     * @param auctionId The ID of the auction this schedule is for
     * @return true if successful, false otherwise
     */
    public static boolean createEventSchedule(String address, String city, String date, 
                                             String startTime, String endTime, int auctionId) {
        String sql = "INSERT INTO EventSchedule (address, city, date, startTime, endTime, auctionId) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, address);
            pstmt.setString(2, city);
            pstmt.setString(3, date);
            pstmt.setString(4, startTime);
            pstmt.setString(5, endTime);
            pstmt.setInt(6, auctionId);
            
            int affectedRows = pstmt.executeUpdate();
            pstmt.close();
            return affectedRows > 0;
            
        } catch (SQLException e) {
            System.out.println("Error creating event schedule: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Gets the most recently created auction ID
     * This is useful for immediately creating a schedule after creating an auction
     * 
     * @return the last inserted auction ID or -1 if an error occurred
     */
    public static int getLastInsertedAuctionId() {
        String sql = "SELECT MAX(auctionID) as last_id FROM normalAuction";
        
        try {
            Connection conn = DatabaseManager.getConnection();
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            int lastId = -1;
            if (rs.next()) {
                lastId = rs.getInt("last_id");
            }
            
            rs.close();
            stmt.close();
            return lastId;
            
        } catch (SQLException e) {
            System.out.println("Error retrieving last auction ID: " + e.getMessage());
            return -1;
        }
    }
}