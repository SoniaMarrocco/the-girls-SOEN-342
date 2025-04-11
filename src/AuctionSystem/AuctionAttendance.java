package AuctionSystem;

import DBaccess.ServiceDB;

public class AuctionAttendance extends Service{
    protected String type;

    public AuctionAttendance() {

    }

    public AuctionAttendance(String type, String date, String startTime, String endTime) {
       super(date, startTime, endTime);
       this.type = type;

    }
    
    public void requestService() {
        ServiceDB.getAllAuctionAttendance();
    }
    
}
