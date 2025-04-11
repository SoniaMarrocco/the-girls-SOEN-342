package DBaccess;

import Database.DatabaseManager;
import java.sql.*;

public class ServiceDB {

    public static void getAllObjectAdvising() {
        try {
            Connection conn = DatabaseManager.getConnection();

            String sql = "SELECT * FROM objectAdvising";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n=== Object Advising Services ===");

            boolean found = false;
            while (rs.next()) {
                int serviceID = rs.getInt("serviceID");
                String type = rs.getString("type");
                String date = rs.getString("date");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                int clientId = rs.getInt("clientId");
                int expertId = rs.getInt("expertId");

                System.out.println("Service ID  : " + serviceID);
                System.out.println("Type        : " + type);
                System.out.println("Date        : " + date);
                System.out.println("Start Time  : " + startTime);
                System.out.println("End Time    : " + endTime);
                System.out.println("-------------------------------");

                found = true;
            }

            if (!found) {
                System.out.println("No object advising services found.");
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Error retrieving object advising data: " + e.getMessage());
        }
    }

    public static void getAllConsultingServices() {
        try {
            Connection conn = DatabaseManager.getConnection();
    
            String sql = "SELECT * FROM consulting";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
    
            System.out.println("\n=== Consulting Services ===");
    
            boolean found = false;
            while (rs.next()) {
                int consultingID = rs.getInt("consultingID");
                String type = rs.getString("type");
                String date = rs.getString("date");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                int clientId = rs.getInt("clientId");
                int expertId = rs.getInt("expertId");
    
                System.out.println("Consulting ID : " + consultingID);
                System.out.println("Type          : " + type);
                System.out.println("Date          : " + date);
                System.out.println("Start Time    : " + startTime);
                System.out.println("End Time      : " + endTime);
                System.out.println("--------------------------------");
    
                found = true;
            }
    
            if (!found) {
                System.out.println("No consulting services found.");
            }
    
            rs.close();
            stmt.close();
    
        } catch (SQLException e) {
            System.err.println("Error retrieving consulting services: " + e.getMessage());
        }
    }

    public static void getAllAuctionAttendance() {
        try {
            Connection conn = DatabaseManager.getConnection();
    
            String sql = "SELECT * FROM auctionAttendance";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
    
            System.out.println("\n=== Auction Attendance Services ===");
    
            boolean found = false;
            while (rs.next()) {
                int attendanceID = rs.getInt("attendanceID");
                String type = rs.getString("type");
                String date = rs.getString("date");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");
                int clientId = rs.getInt("clientId");
                int expertId = rs.getInt("expertId");
    
                System.out.println("Attendance ID : " + attendanceID);
                System.out.println("Type          : " + type);
                System.out.println("Date          : " + date);
                System.out.println("Start Time    : " + startTime);
                System.out.println("End Time      : " + endTime);
                System.out.println("-----------------------------------");
    
                found = true;
            }
    
            if (!found) {
                System.out.println("No auction attendance services found.");
            }
    
            rs.close();
            stmt.close();
    
        } catch (SQLException e) {
            System.err.println("Error retrieving auction attendance services: " + e.getMessage());
        }
    }

    
    
}