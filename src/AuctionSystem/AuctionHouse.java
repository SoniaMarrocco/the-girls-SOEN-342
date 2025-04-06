package AuctionSystem;
public class AuctionHouse {
    private int auctionHouse_id;
    private String name;
    public Objects[] obj;

    public AuctionHouse(int auctionHouse_id, String name, Objects[] obj) {
        this.auctionHouse_id = auctionHouse_id;
        this.name = name;
        this.obj = obj;
    }

    public int getAuctionHouse_id() {
        return auctionHouse_id;
    }

    public void setAuctionHouse_id(int auctionHouse_id) {
        this.auctionHouse_id = auctionHouse_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Objects[] getObj() {
        return obj;
    }

    public void setObj(Objects[] obj) {
        this.obj = obj;
    }

    public void viewingRegistration(){}

}
