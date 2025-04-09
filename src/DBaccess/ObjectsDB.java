package DBaccess;

import AuctionSystem.Objects;
import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ObjectsDB {

    public static void printAllObjects() throws Exception {
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
}
