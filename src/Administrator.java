import java.util.ArrayList;

public class Administrator {
    private String name;
    public ArrayList<Client> clients;
    public AuctionHouse[] ah;
    public Expert[] e;
    public static ArrayList<Objects> obj;
    private static Administrator admin;

    private Administrator(String name) {
        this.name = name;
        this.clients = new ArrayList<>();
    }

    public static Administrator getInstance(String name) {
        if (admin == null) {
            // Lazy initialization: only create the instance when it's first needed
            admin = new Administrator(name);
        }
        return admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public ArrayList<Client> setClients(ArrayList<Client> clients) {
        return this.clients = clients;
    }

    public AuctionHouse[] getAh() {
        return ah;
    }

    public void setAh(AuctionHouse[] ah) {
        this.ah = ah;
    }

    public Expert[] getE() {
        return e;
    }

    public void setE(Expert[] e) {
        this.e = e;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(ArrayList<Objects> obj) {
        this.obj = obj;
    }

    public void createAccount(String name, String email, String password, String affiliation, String contact_info, String description) {
        Client client = new Client(name, email, password, affiliation, contact_info, description);
        Boolean check = this.approveClientSignUp();
        if (check == true) {
            clients.add(client);
            System.out.print("Sign up successful!");
        }
        else {
            System.out.print("Sign up was rejected");
        }
    }

    public boolean approveClientSignUp() {
        return true;
    }
}
