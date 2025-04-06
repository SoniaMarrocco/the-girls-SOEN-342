package AuctionSystem;
import java.util.ArrayList;

public class Auction {
    private String specialty;
    private String auction_title;
    private ArrayList<Auction> auctions;
    public static ArrayList<Objects> objects;
    
    public Auction(String specialty, String auction_title) {
        this.specialty = specialty;
        this.auction_title = auction_title;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getAuction_title() {
        return auction_title;
    }

    public void setAuction_title(String auction_title) {
        this.auction_title = auction_title;
    }

    public ArrayList<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(ArrayList<Auction> auctions) {
        this.auctions = auctions;
    }

    public static ArrayList<Objects> getObjects() {
        return objects;
    }

    public static void setObjects(ArrayList<Objects> objects) {
        Auction.objects = objects;
    }

    public void searchAuction()  {
        if (auctions.size() != 0) {
            for (int i = 0; i < auctions.size(); i++) {
                System.out.print(i + 1 + auctions.get(i).toString());
            }
        }
        else {
            System.out.println("No auctions exist in the system");
        }
    }

    public void selectAuction(int i) {
        auctions.get(i).toString();
    }

    public String toString() {
        return this.auction_title;    
    }
    
    

}
