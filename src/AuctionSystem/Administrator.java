package AuctionSystem;

import DBaccess.AdminDB;

import java.util.ArrayList;

public class Administrator {
    private String name;
    private ArrayList<Client> clients;
    private static Administrator adminInstance;

    private Administrator(String name) {
        this.name = name;
        this.clients = new ArrayList<>();
    }

    public static Administrator getInstance(String name) {
        if (adminInstance == null) {
            adminInstance = new Administrator(name);
        }
        return adminInstance;
    }

    public void createAccount(String name, String email, String password, String affiliation, String contactInfo, String description) {
        Client newClient = new Client(name, email, password, affiliation, contactInfo, description);
        boolean approved = approveClientSignUp(newClient);

        if (approved) {
            clients.add(newClient);
            System.out.println("Sign up successful!");
        } else {
            System.out.println("Sign up was rejected.");
        }
    }

    public boolean approveClientSignUp(Client client) {
        try {
            // Check if already registered or password too short
            if (AdminDB.isEmailRegistered(client.getEmail())) {
                System.out.println("Email is already registered.");
                return false;
            }

            if (client.getPassword().length() < 8) {
                System.out.println("Password must be at least 8 characters long.");
                return false;
            }

            return AdminDB.insertClient(client);
        } catch (Exception e) {
            System.err.println("Error during client sign-up approval: " + e.getMessage());
            return false;
        }
    }

    // --- Getters & Setters ---

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public ArrayList<Client> getClients() { return clients; }
    public void setClients(ArrayList<Client> clients) { this.clients = clients; }
}
