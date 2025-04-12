package DBaccess;

import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class eventScheduleDB {

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