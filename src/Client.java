import java.util.ArrayList;

public class Client {
    private String name;
    private String email;
    private String password;
    private String affiliation;
    private String contact_info;
    private String description;

    public ArrayList<Objects> obj;
    public ArrayList<AuctionHouse> auction_house;
    public ArrayList<Auction> auction;
    public ArrayList<Viewing> viewing;
    public ArrayList<Service> service = new ArrayList<>();
    private static Client loggedInClient = null;

    public Client(String name, String email, String password, String affiliation, String contact_info, String description) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.affiliation = affiliation;
        this.contact_info = contact_info;
        this.description = description;
    }


    //methods

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }



    public String getPassword() {
        return password;
    }



    public void setPassword(String password) {
        this.password = password;
    }



    public String getAffiliation() {
        return affiliation;
    }



    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }



    public String getContact_info() {
        return contact_info;
    }



    public void setContact_info(String contact_info) {
        this.contact_info = contact_info;
    }



    public String getDescription() {
        return description;
    }



    public void setDescription(String description) {
        this.description = description;
    }



    public ArrayList<Objects> getObj() {
        return obj;
    }



    public void setObj(ArrayList<Objects> obj) {
        this.obj = obj;
    }



    public ArrayList<AuctionHouse> getAuction_house() {
        return auction_house;
    }



    public void setAuction_house(ArrayList<AuctionHouse> auction_house) {
        this.auction_house = auction_house;
    }



    public ArrayList<Auction> getAuction() {
        return auction;
    }



    public void setAuction(ArrayList<Auction> auction) {
        this.auction = auction;
    }



    public ArrayList<Viewing> getViewing() {
        return viewing;
    }



    public void setViewing(ArrayList<Viewing> viewing) {
        this.viewing = viewing;
    }



    public ArrayList<Service> getService() {
        return service;
    }


    public void setService(ArrayList<Service> service) {
        this.service = service;
    }

    public static Client getLoggedInClient() {
        return loggedInClient;
    }


    public static void setLoggedInClient(Client loggedInClient) {
        Client.loggedInClient = loggedInClient;
    }


    public static void login(String email, String password, ArrayList<Client> clients) {
        for (Client client : clients) {
            if (client.email.equals(email) && client.password. equals (password)) {
                loggedInClient = client;
                System.out.println("Login successful! Welcome, " + client. getName ());
                return;
            }
        }
        System.out.println("Invalid email or password.");
}

    public void client_login(String email, String password) {

    }

    //for log in
    public void validateCredentials() {

    }


}
