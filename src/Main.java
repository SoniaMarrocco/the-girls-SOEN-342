import AuctionSystem.Client;
import Database.DatabaseManager;
import java.sql.SQLException;
import java.util.Scanner;

import static AuctionSystem.Client.client_login;

public class Main {
    public static void main(String[] args) {
        Client client;
        Scanner scanner = new Scanner(System.in);
        try {
            // Just initialize the connection through your existing DatabaseManager
            // This sets up the connection for the entire application
            DatabaseManager.getConnection();
            System.out.println("Connected to the database.");

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }


        while(true) {
            System.out.println("Welcome to the Auction system");
            System.out.print("[1] Signup \n[2] Login \n[3] Exit System\n");

            int option = scanner.nextInt();

            if (option ==1)
                System.out.println("1");
            else if (option ==2) {
                System.out.print("Enter your email: ");
                String x = scanner.nextLine();
                String email = scanner.nextLine();
                System.out.print("Enter your password: ");

                String pass = scanner.nextLine();
                client = client_login(email,pass);
            }
            else if (option ==3) {
                System.out.println("3");
            }
            else
                System.out.print("enter a valid input please\n");


        }

    }
}