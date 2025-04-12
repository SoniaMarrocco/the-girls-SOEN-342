package DBaccess;

import AuctionSystem.Auction;
import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuctionDB {

    public static void getAllAuctions() throws Exception {
        Connection conn = DatabaseManager.getConnection();
        String query = "SELECT auctionID, auctionTitle, speciality FROM normalAuction " +
                "UNION " +
                "SELECT auctionID, auctionTitle, speciality FROM onlineAuction";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        int count = 0;
        while (rs.next()) {
            System.out.print("["+(count+1)+"]");
            int id = rs.getInt("auctionID");
            String title = rs.getString("auctionTitle");
            String specialty = rs.getString("speciality");

            Auction auction = new Auction(specialty, title);
            System.out.println(auction);
            count++;
        }

        if (count == 0) {
            System.out.println("No auctions available.");
        }

        rs.close();
        stmt.close();
    }

    public static Auction getAuctionById(int auctionId) throws Exception {
        Connection conn = DatabaseManager.getConnection();

        String query = "SELECT auctionID, auctionTitle, speciality FROM normalAuction WHERE auctionID = ? " +
                "UNION " +
                "SELECT auctionID, auctionTitle, speciality FROM onlineAuction WHERE auctionID = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, auctionId);
        stmt.setInt(2, auctionId);

        ResultSet rs = stmt.executeQuery();
        Auction auction = null;

        if (rs.next()) {
            String title = rs.getString("auctionTitle");
            String specialty = rs.getString("speciality");
            int id = rs.getInt("auctionID");

            auction = new Auction( specialty, title);
        }

        rs.close();
        stmt.close();
        return auction;
    }
}
