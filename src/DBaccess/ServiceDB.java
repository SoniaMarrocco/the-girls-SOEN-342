package DBaccess;

import AuctionSystem.Client;
import Database.DatabaseManager;
import java.sql.*;

public class ServiceDB {

    private static void getAllServices(String table, String idCol) {
        try {
            Connection conn = DatabaseManager.getConnection();

            String sql = "SELECT * FROM " + table;
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n=== " + table.toUpperCase() + " Services ===");

            boolean found = false;
            while (rs.next()) {
                int serviceId = rs.getInt(idCol);
                String type = rs.getString("type");
                String date = rs.getString("date");
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");

                System.out.println("Service ID  : " + serviceId);
                System.out.println("Type        : " + type);
                System.out.println("Date        : " + date);
                System.out.println("Start Time  : " + startTime);
                System.out.println("End Time    : " + endTime);
                System.out.println("----------------------------------");

                found = true;
            }

            if (!found) {
                System.out.println("No services found in " + table + ".");
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("Error retrieving " + table + " services: " + e.getMessage());
        }
    }


    public static void getAllObjectAdvising() {
        getAllServices("objectAdvising", "serviceID");
    }

    public static void getAllConsultingServices() {
        getAllServices("consulting", "consultingID");
    }

    public static void getAllAuctionAttendance() {
        getAllServices("auctionAttendance", "attendanceID");
    }


    public static void updateObjectAdvising(Client client, int serviceId) {
        updateService(client, serviceId, "objectAdvising", "serviceID");
    }

    public static void updateConsulting(Client client, int serviceId) {
        updateService(client, serviceId, "consulting", "consultingID");
    }

    public static void updateAuctionAttendance(Client client, int serviceId) {
        updateService(client, serviceId, "auctionAttendance", "attendanceID");
    }

    private static void updateService(Client client, int serviceId, String table, String idCol) {
        try {
            Connection conn = DatabaseManager.getConnection();

            // Look up clientId using email
            String getClientSql = "SELECT clientId FROM Client WHERE email = ?";
            PreparedStatement getClientStmt = conn.prepareStatement(getClientSql);
            getClientStmt.setString(1, client.getEmail());
            ResultSet rs = getClientStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Client not found.");
                return;
            }

            int clientId = rs.getInt("clientId");
            rs.close();
            getClientStmt.close();

            // Update service entry
            String updateSql = "UPDATE " + table + " SET clientId = ? WHERE " + idCol + " = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setInt(1, clientId);
            updateStmt.setInt(2, serviceId);

            int rows = updateStmt.executeUpdate();
            updateStmt.close();

            if (rows > 0) {
                System.out.println("Successfully booked service ID " + serviceId + " in " + table + ".");
            } else {
                System.out.println("Service booking failed. Check if the ID exists.");
            }

        } catch (SQLException e) {
            System.err.println("Error updating service: " + e.getMessage());
        }
    }




}