package AuctionSystem;
import java.util.ArrayList;

// Abstract class since 'Service' should not be instantiated directly
public abstract class Service {
   protected String date;
   protected String startTime;
   protected String endTime;
   protected Client loggedInClient;

   // Constructor
   public Service() {
   }

   public Service(String date, String startTime, String endTime) {
       this.date = date;
       this.startTime = startTime;
       this.endTime = endTime;
   }

   // Abstract methods ensure subclasses provide their own implementations
   public abstract void requestService();
   public void clientBookServiceTime(Client loggedInClient) {
    
   }
}






