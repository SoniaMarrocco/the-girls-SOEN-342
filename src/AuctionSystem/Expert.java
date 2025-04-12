package AuctionSystem;

import DBaccess.ExpertDB;

import java.util.Scanner;

public class Expert {
    private String name;
    private String email;
    private String username;
    private String password;
    private String contact;
    private int licenceNum;
    private String specialty;

    public Expert(String name, String email, String username, String password, String contact, int licenceNum, String specialty) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.contact = contact;
        this.licenceNum = licenceNum;
        this.specialty = specialty;
    }

    // Expert Login (modeled like Client.login)
    public static Expert login() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter your username: ");
            String username = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            try {
                Expert expert = ExpertDB.getExpertByCredentials(username, password);

                if (expert != null) {
                    System.out.println("Login successful! Welcome, " + expert.getName());
                    return expert;
                } else {
                    System.out.println("Invalid username or password. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Error during expert login: " + e.getMessage());
            }
        }
    }

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public int getLicenceNum() { return licenceNum; }
    public void setLicenceNum(int licenceNum) { this.licenceNum = licenceNum; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
}
