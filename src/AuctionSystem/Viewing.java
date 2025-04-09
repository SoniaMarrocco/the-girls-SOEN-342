package AuctionSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Database.DatabaseManager;

public class Viewing {
    private int viewingId;
    private String catalog;

    public Viewing() {
        this.catalog = null;
        this.viewingId = 0;
    }

    public Viewing(int viewingId) {
        this.catalog = null;
        this.viewingId = viewingId;
    }

    public void viewingRegistration(...) {

    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public int getViewingId() {
        return viewingId;
    }

    public void setViewingId(int viewingId) {
        this.viewingId = viewingId;
    }
}