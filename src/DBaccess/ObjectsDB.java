package DBaccess;

import AuctionSystem.Objects;
import AuctionSystem.Viewing;
import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectsDB {

    public static void getAllObjects() throws Exception {
        Connection conn = DatabaseManager.getConnection();
        String query = "SELECT objectsId, name, description, available FROM Objects";

        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        int count = 0;

        while (rs.next()) {
            count++;
            int id = rs.getInt("objectsId");
            String name = rs.getString("name");
            String desc = rs.getString("description");
            boolean available = rs.getBoolean("available");

            System.out.println(count + ". Object ID: " + id);
            System.out.println("   Name: " + name);
            System.out.println("   Description: " + desc);
            System.out.println("   Available: " + (available ? "Yes" : "No"));
            System.out.println();
        }

        if (count == 0) {
            System.out.println("No objects exist in the system.");
        }

        rs.close();
        stmt.close();
    }

    public static Objects getObjectById(int objectId) throws Exception {
        Connection conn = DatabaseManager.getConnection();
        String query = "SELECT name, description, available FROM Objects WHERE objectsId = ?";

        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, objectId);

        ResultSet rs = stmt.executeQuery();
        Objects object = null;

        if (rs.next()) {
            String name = rs.getString("name");
            String desc = rs.getString("description");
            boolean available = rs.getBoolean("available");

            object = new Objects(name, desc, available);
        }

        rs.close();
        stmt.close();

        return object;
    }
    public static Viewing getViewingFromObject(int objectId) throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        String sql = "SELECT v.viewingId " +
                "FROM Objects o " +
                "JOIN normalAuction n ON o.normalAuctionId = n.auctionID " +
                "JOIN viewing v ON n.viewingId = v.viewingId " +
                "WHERE o.objectsId = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, objectId);
        ResultSet rs = stmt.executeQuery();

        Viewing viewing = null;

        if (rs.next()) {
            int viewingId = rs.getInt("viewingId");
            viewing = new Viewing(viewingId);
        }

        rs.close();
        stmt.close();
        return viewing;
    }
    public static boolean isObjectInAuction(int objectId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean inAuction = false;

        try {
            conn = DatabaseManager.getConnection();

            // Query to check if the normalAuctionID is null
            String query = "SELECT normalAuctionID FROM Objects WHERE objectsId = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, objectId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                // Check if normalAuctionID is not null
                Object auctionId = rs.getObject("normalAuctionID");
                inAuction = (auctionId != null);
            }

        } catch (SQLException e) {
            System.err.println("Database error while checking auction status: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }

        return inAuction;
    }
}
