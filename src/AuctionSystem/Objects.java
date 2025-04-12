package AuctionSystem;

import DBaccess.ObjectsDB;

public class Objects {
    private String name;
    private String description;
    private Boolean available;

    // Constructor
    public Objects(String name, String description, Boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }

    public Objects(){

    }

    public static void searchObject() {
        try {
            ObjectsDB.getAllObjects();  // Just prints them from DB
        } catch (Exception e) {
            System.err.println("Error searching for objects: " + e.getMessage());
        }
    }

    public Objects selectObject(int objectId) {
        try {
            return ObjectsDB.getObjectById(objectId);  // Loads object from DB
        } catch (Exception e) {
            System.err.println("Error selecting object: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nDescription: " + description + "\nAvailable: " + (available ? "Yes" : "No");
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

}
