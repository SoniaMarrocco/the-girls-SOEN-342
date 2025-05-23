package AuctionSystem;

import DBaccess.ServiceDB;

public class ObjectAdvising extends Service{
    protected String type;

    public ObjectAdvising() {super();}

    public ObjectAdvising(String type, String date, String startTime, String endTime) {
       super(date, startTime, endTime);
       this.type = type;

    }
    
    public void requestService() {
         ServiceDB.getAllObjectAdvising();

    }

    public void clientBookServiceTime(Client loggedInClient, int serviceId) {
        ServiceDB.updateObjectAdvising(loggedInClient, serviceId);
    }
}
