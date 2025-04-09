package AuctionSystem;

import DBaccess.ExpertDB;

public class Expert {
    private String name;
    private String email;
    private String username;
    private String password;
    private String contact;
    private int licenceNum;
    private int[] specialty;
    public Objects[] objs;

    private static Expert loggedInExpert;

    public Expert(String name, String email, String username, String password, String contact, int licenceNum, int[] specialty, Objects[] objs) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.contact = contact;
        this.licenceNum = licenceNum;
        this.specialty = specialty;
        this.objs = objs;
    }

    // Login Logic
    public static Expert expertLogin(String username, String password) {
        try {
            Expert expert = ExpertDB.getExpertByCredentials(username, password);
            if (expert != null) {
                loggedInExpert = expert;
                System.out.println("Login successful. Welcome, " + expert.getName() + "!");
                return expert;
            } else {
                System.out.println("Invalid username or password.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error during expert login: " + e.getMessage());
            return null;
        }
    }


    public static Expert getLoggedInExpert() {
        return loggedInExpert;
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

    public int[] getSpecialty() { return specialty; }
    public void setSpecialty(int[] specialty) { this.specialty = specialty; }
}
