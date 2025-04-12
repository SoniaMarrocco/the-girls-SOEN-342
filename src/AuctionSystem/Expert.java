package AuctionSystem;

import DBaccess.ExpertDB;
import DBaccess.ServiceDB;

import java.util.Scanner;

public class Expert {
    private String name;
    private String email;
    private String password;
    private String contact;
    private int licenceNum;
    private String specialty;

    public Expert(String name, String email, String password, String contact, int licenceNum, String specialty) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.contact = contact;
        this.licenceNum = licenceNum;
        this.specialty = specialty;
    }

    public static Expert login() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            System.out.print("Enter your password: ");
            String password = scanner.nextLine();

            try {
                Expert expert = ExpertDB.getExpertByCredentials(email, password);

                if (expert != null) {
                    System.out.println("Login successful! Welcome, " + expert.getName());
                    return expert;
                } else {
                    System.out.println("Invalid email or password. Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Error during expert login: " + e.getMessage());
            }
        }
    }

    public void createService() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWhich type of service would you like to create?");
        System.out.println("[1] Object Advising");
        System.out.println("[2] Consulting");
        System.out.println("[3] Auction Attendance");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.print("Enter start time (HH:MM): ");
        String startTime = scanner.nextLine();

        System.out.print("Enter end time (HH:MM): ");
        String endTime = scanner.nextLine();

        System.out.print("Enter type of service (description): ");
        String type = scanner.nextLine();

        // Use license number to get DB expert ID
        int expertId = ExpertDB.getExpertIdByLicenseNum(this.getLicenceNum());

        if (expertId == -1) {
            System.out.println("Failed to locate expert ID. Service creation aborted.");
            return;
        }

        switch (choice) {
            case 1 -> ServiceDB.insertObjectAdvising(type, date, startTime, endTime, expertId);
            case 2 -> ServiceDB.insertConsulting(type, date, startTime, endTime, expertId);
            case 3 -> ServiceDB.insertAuctionAttendance(type, date, startTime, endTime, expertId);
            default -> System.out.println("Invalid choice. No service created.");
        }

        System.out.println("Service creation complete.");
    }



    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public int getLicenceNum() { return licenceNum; }
    public void setLicenceNum(int licenceNum) { this.licenceNum = licenceNum; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
}
