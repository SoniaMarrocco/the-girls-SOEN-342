import java.util.ArrayList;

// Abstract class since 'Service' should not be instantiated directly
abstract class Service {
    protected String type;
    protected String date;
    protected String time;
    protected Client bookedBy;

    // Constructor
    public Service() {
    }
    
    public Service(String type, String date, String time) {
        this.type = type;
        this.date = date;
        this.time = time;
    }

    // Abstract methods ensure subclasses provide their own implementations
    public abstract void requestService();
    public abstract void clientBookServiceTime(int i);
}

// ObjectAdvising subclass
class ObjectAdvising extends Service {
    public static ArrayList<ObjectAdvising> objectAdvisings;

    public ObjectAdvising() {
        super();
    }

    public ObjectAdvising(String type, String date, String time) {
        super(type, date, time);
    }

    @Override
    public void requestService() {
        for (int i = 0; i < objectAdvisings.size(); i++) {
            System.out.print(i + 1 + objectAdvisings.get(i).toString());
        }
    }

    @Override
    public void clientBookServiceTime(int i) {
        Client loggedInClient = Client.getLoggedInClient();
        ObjectAdvising selectedService = objectAdvisings.get(i);
        bookedBy = loggedInClient;
        bookedBy.getService().add(selectedService);
        System.out.println("Object Advising Service booked for " + type + " at " + date + " " + time);
    }

    public String toString() {
        return this.type + ": At" + this.time + ", on" + this.date;
    }
}

// Consulting subclass
class Consulting extends Service {
    public static ArrayList<Consulting> consultings;

    public Consulting() {
        super();
    }

    public Consulting(String type, String date, String time) {
        super(type, date, time);
    }

    @Override
    public void requestService() {
        for (int i = 0; i < consultings.size(); i++) {
            System.out.print(i + 1 + consultings.get(i).toString());
        }
    }

    @Override
    public void clientBookServiceTime(int i) {
        Client loggedInClient = Client.getLoggedInClient();
        Consulting selectedService = consultings.get(i);
        bookedBy = loggedInClient;
        bookedBy.getService().add(selectedService);
        System.out.println("Object Advising Service booked for " + type + " at " + date + " " + time);
    }

    public String toString() {
        return this.type + ": At" + this.time + ", on" + this.date;
    }
}

// AuctionAttendance subclass
class AuctionAttendance extends Service {
    public static ArrayList<AuctionAttendance> auctionAttendences;

    public AuctionAttendance() {
        super();
    }

    public AuctionAttendance(String type, String date, String time) {
        super(type, date, time);
    }

    @Override
    public void requestService() {
        for (int i = 0; i < auctionAttendences.size(); i++) {
            System.out.print(i + 1 + auctionAttendences.get(i).toString());
        }
    }

    @Override
    public void clientBookServiceTime(int i) {
        Client loggedInClient = Client.getLoggedInClient();
        AuctionAttendance selectedService = auctionAttendences.get(i);
        bookedBy = loggedInClient;
        bookedBy.getService().add(selectedService);
        System.out.println("Object Advising Service booked for " + type + " at " + date + " " + time);
    }

    public String toString() {
        return this.type + ": At" + this.time + ", on" + this.date;
    }


}


