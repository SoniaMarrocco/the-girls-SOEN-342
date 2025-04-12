package AuctionSystem;

import DBaccess.AuctionDB;

public class Auction {
    private String specialty;
    private String auctionTitle;
    public Viewing viewing;

    public Auction( String specialty, String auctionTitle) {
        this.specialty = specialty;
        this.auctionTitle = auctionTitle;
    }
    public Auction(){
    }
    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getAuctionTitle() {
        return auctionTitle;
    }

    public void setAuctionTitle(String auctionTitle) {
        this.auctionTitle = auctionTitle;
    }

    public Viewing getViewing() {
        return viewing;
    }

    public void setViewing(Viewing viewing) {
        this.viewing = viewing;
    }

    public static void searchAuction() {
        try {
            AuctionDB.getAllAuctions();
        } catch (Exception e) {
            System.err.println("Error retrieving auctions: " + e.getMessage());
        }
    }

    public Auction selectAuction(int auctionID) {
        try {
            Auction auction = AuctionDB.getAuctionById(auctionID);
            if (auction != null) {
                System.out.println("\n--- Auction Details ---");
                System.out.println("Title     : " + auction.getAuctionTitle());
                System.out.println("Specialty : " + auction.getSpecialty());
                System.out.println("------------------------\n");
            } else {
                System.out.println("Auction not found with ID: " + auctionID);
            }
            return auction;
        } catch (Exception e) {
            System.err.println("Error selecting auction: " + e.getMessage());
            return null;
        }
    }



    @Override
    public String toString() {
        return  auctionTitle + " (" + specialty + ")";
    }
}

