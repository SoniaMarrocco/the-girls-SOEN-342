package AuctionSystem;

import DBaccess.ServiceDB;

public class Consulting extends Service{
    protected String type;

    public Consulting() {super();}

    public Consulting(String type, String date, String startTime, String endTime) {
       super(date, startTime, endTime);
       this.type = type;

    }
    
    public void requestService() {
         ServiceDB.getAllConsultingServices();

    }

    public void clientBookServiceTime(Client loggedInClient, int serviceId) {
        ServiceDB.updateConsulting(loggedInClient, serviceId);
    }
}
