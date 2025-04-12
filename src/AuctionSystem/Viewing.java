package AuctionSystem;

import DBaccess.ViewingDB;

public class Viewing {
    private int viewingId;
    private String catalogName;

    public Viewing() {}

    public Viewing(int viewingId) {
        this.viewingId = viewingId;
    }

    public void viewingRegistration(Client client) {
        boolean success = ViewingDB.registerClientToViewing(client, this.viewingId);
        if (success) {
            System.out.println("Successfully registered for the viewing.");
        } else {
            System.out.println("Registration failed or already exists.");
        }
    }

    public int getViewingId() {
        return viewingId;
    }

    public void setViewingId(int viewingId) {
        this.viewingId = viewingId;
    }

    public String getCatalog() {
        return catalogName;
    }

    public void setCatalog(String catalogName) {
        this.catalogName = catalogName;
    }
}
