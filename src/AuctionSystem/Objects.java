package AuctionSystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Database.DatabaseManager;

import java.util.ArrayList;

public class Objects {
    private String name;
    private String description;
    private Boolean available;
    private ArrayList<Object> objects;

    public Objects(String name, String description, Boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }


    public void makeObjectAvailable(int obj_Id){}

    public static void searchObject() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Establish connection to your database
            conn = DatabaseManager.getConnection(); // Assuming you have a connection utility class

            // Create a query to select all objects
            String query = "SELECT objectsId, name, description FROM Objects";
            stmt = conn.prepareStatement(query);

            // Execute the query
            rs = stmt.executeQuery();

            int count = 0;

            // Process the results
            while (rs.next()) {
                count++;
                int objectId = rs.getInt("objectsId");
                String name = rs.getString("name");
                String description = rs.getString("description");

                System.out.println(count + ". Object ID: " + objectId);
                System.out.println("   Name: " + name);
                System.out.println("   Description: " + description);
                System.out.println();
            }

            if (count == 0) {
                System.out.println("No objects exist in the system");
            }

        } catch (SQLException e) {
            System.err.println("Error searching for objects: " + e.getMessage());
        } finally {
            // Close resources to prevent memory leaks
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing database resources: " + e.getMessage());
            }
        }
    }

    public void selectObject(int i) {
        objects.get(i).toString();
    }

    public String toString() {
        return this.description;    
    }

    

}
