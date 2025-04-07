import AuctionSystem.*;
import Database.DatabaseManager;
import java.sql.SQLException;
import java.util.Scanner;

import static AuctionSystem.Client.client_login;

public class Main {
    public static void main(String[] args) {
        Client loggedInClient;
        Administrator admin = Administrator.getInstance("Admin");

        Scanner scanner = new Scanner(System.in);
        try {
            // Just initialize the connection through your existing DatabaseManager
            // This sets up the connection for the entire application
            DatabaseManager.getConnection();
            System.out.println("Connected to the database.");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }

        System.out.println("Welcome to the Auction system!\n ");
        while(true) {
            System.out.println("\nHome Page");
            System.out.print("[1] Signup \n[2] Login \n[3] Enter System as Admin \n[4] Exit System\n");

            int option = scanner.nextInt();

            if (option ==1){
                System.out.println("Please enter all the information below:/n");
                String garbage = scanner.nextLine();
                System.out.println("Name: ");
                String name = scanner.nextLine();
                System.out.println("Email: ");
                String email = scanner.nextLine();
                System.out.println("Password (Must be greater than 8 characters): ");
                String pass = scanner.nextLine();
                System.out.println("Affiliation: ");
                String aff = scanner.nextLine();
                System.out.println("Contact Information: ");
                String contact = scanner.nextLine();
                System.out.println("Description: ");
                String desc = scanner.nextLine();
                admin.createAccount(name, email, pass, aff,contact, desc);

            }

            else if (option ==2) {
                System.out.print("Enter your email: ");
                String garbage = scanner.nextLine();
                String email = scanner.nextLine();
                System.out.print("Enter your password: ");
                String pass = scanner.nextLine();
                loggedInClient = client_login(email,pass);
            }
            else if (option ==3) {
                System.out.println("3");
            }
            else if (option == 4){
                break;
            }
            else
                System.out.print("enter a valid input please\n");


        }

        System.out.println("\nThank you for using the Auction System!");
    }
}