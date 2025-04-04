package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDBCreator {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:Database/auction_system.db"; // Database file path

        try (Connection conn = DriverManager.getConnection(url); 
             Statement stmt = conn.createStatement()) {
            
            if (conn != null) {
                System.out.println("Connected to SQLite database.");
            }

            // Creating tables (as you already have)
            String[] createTables = {
                "CREATE TABLE IF NOT EXISTS Administrator (adminID INTEGER PRIMARY KEY, name TEXT);",
                "CREATE TABLE IF NOT EXISTS Client (clientId INTEGER PRIMARY KEY, email TEXT, password TEXT, affiliation TEXT, contactInfo TEXT, description TEXT);",
                "CREATE TABLE IF NOT EXISTS Client_Auction (client INTEGER, auctionID INTEGER, FOREIGN KEY(client) REFERENCES Client(clientId), FOREIGN KEY(auctionID) REFERENCES normalAuction(auctionID));",
                "CREATE TABLE IF NOT EXISTS normalAuction (auctionID INTEGER PRIMARY KEY, speciality TEXT, auctionTitle TEXT, auctionHouseId INTEGER, viewingId INTEGER, FOREIGN KEY(auctionHouseId) REFERENCES AuctionHouse(auctionHouseId), FOREIGN KEY(viewingId) REFERENCES viewing(viewingId));",
                "CREATE TABLE IF NOT EXISTS onlineAuction (auctionID INTEGER PRIMARY KEY, speciality TEXT, auctionTitle TEXT, auctionHouseId INTEGER, FOREIGN KEY(auctionHouseId) REFERENCES AuctionHouse(auctionHouseId));",
                "CREATE TABLE IF NOT EXISTS Client_Viewing (clientId INTEGER, viewingId INTEGER, FOREIGN KEY(clientId) REFERENCES Client(clientId), FOREIGN KEY(viewingId) REFERENCES viewing(viewingId));",
                "CREATE TABLE IF NOT EXISTS viewing (viewingId INTEGER PRIMARY KEY, auctionHouseId INTEGER, FOREIGN KEY(auctionHouseId) REFERENCES AuctionHouse(auctionHouseId));",
                "CREATE TABLE IF NOT EXISTS AuctionHouse (auctionHouseId INTEGER PRIMARY KEY, name TEXT);",
                "CREATE TABLE IF NOT EXISTS Objects (objectsId INTEGER PRIMARY KEY, description TEXT, available BOOLEAN, auctionHouseID INTEGER, auctionId INTEGER, FOREIGN KEY(auctionHouseID) REFERENCES AuctionHouse(auctionHouseId), FOREIGN KEY(auctionId) REFERENCES normalAuction(auctionID));",
                "CREATE TABLE IF NOT EXISTS Expert (expertId INTEGER PRIMARY KEY, name TEXT, contact TEXT, licenseNum TEXT, specialty TEXT);",
                "CREATE TABLE IF NOT EXISTS objectAdvising (serviceID INTEGER PRIMARY KEY, type TEXT, time TEXT, clientId INTEGER, expertId INTEGER, FOREIGN KEY(clientId) REFERENCES Client(clientId), FOREIGN KEY(expertId) REFERENCES Expert(expertId));",
                "CREATE TABLE IF NOT EXISTS consulting (consultingID INTEGER PRIMARY KEY, type TEXT, time TEXT, clientId INTEGER, expertId INTEGER, FOREIGN KEY(clientId) REFERENCES Client(clientId), FOREIGN KEY(expertId) REFERENCES Expert(expertId));",
                "CREATE TABLE IF NOT EXISTS auctionAttendance (attendanceID INTEGER PRIMARY KEY, type TEXT, time TEXT, clientId INTEGER, expertId INTEGER, FOREIGN KEY(clientId) REFERENCES Client(clientId), FOREIGN KEY(expertId) REFERENCES Expert(expertId));",
                "CREATE TABLE IF NOT EXISTS EventSchedule (locationId INTEGER, scheduleId INTEGER, auctionId INTEGER, PRIMARY KEY(locationId, scheduleId), FOREIGN KEY(locationId) REFERENCES Location(locationId), FOREIGN KEY(scheduleId) REFERENCES Schedule(scheduleId), FOREIGN KEY(auctionId) REFERENCES normalAuction(auctionID));",
                "CREATE TABLE IF NOT EXISTS Location (locationId INTEGER PRIMARY KEY, address TEXT, city TEXT);",
                "CREATE TABLE IF NOT EXISTS Schedule (scheduleId INTEGER PRIMARY KEY, date TEXT, time TEXT);"
            };

            // Execute table creation queries
            for (String sql : createTables) {
                stmt.execute(sql);
            }

            System.out.println("Database and tables created successfully.");
            
            // Insert AuctionHouse data
            String[] auctionHouses = {
                "INSERT INTO AuctionHouse (name) VALUES ('Sotheby''s');",
                "INSERT INTO AuctionHouse (name) VALUES ('Christie''s');"
            };
            for (String sql : auctionHouses) {
                stmt.executeUpdate(sql);
            }

            // Insert normal auctions data
            String[] auctions = {
                "INSERT INTO normalAuction (speciality, auctionTitle, auctionHouseId) VALUES ('Art', 'Modern Art Auction', 1);",
                "INSERT INTO normalAuction (speciality, auctionTitle, auctionHouseId) VALUES ('Antiques', 'Antique Auction', 2);"
            };
            for (String sql : auctions) {
                stmt.executeUpdate(sql);
            }

            // Insert Clients
            String[] clients = {
                "INSERT INTO Client (email, password, affiliation, contactInfo, description) VALUES ('alice@example.com', 'password1', 'Gallery A', '123-456-7890', 'Art collector');",
                "INSERT INTO Client (email, password, affiliation, contactInfo, description) VALUES ('bob@example.com', 'password2', 'Museum B', '987-654-3210', 'Antique enthusiast');"
            };
            for (String sql : clients) {
                stmt.executeUpdate(sql);
            }

            // Insert Experts
            String[] experts = {
                "INSERT INTO Expert (name, contact, licenseNum, specialty) VALUES ('John Doe', 'john.doe@example.com', 'EX12345', 'Art Expert');",
                "INSERT INTO Expert (name, contact, licenseNum, specialty) VALUES ('Jane Smith', 'jane.smith@example.com', 'EX67890', 'Antique Expert');"
            };
            for (String sql : experts) {
                stmt.executeUpdate(sql);
            }

            // Insert Services (Object Advising, Consulting, Auction Attendance)
            String[] services = {
                "INSERT INTO objectAdvising (type, time, clientId, expertId) VALUES ('Art valuation', '2024-04-10 10:00', 1, 1);",
                "INSERT INTO consulting (type, time, clientId, expertId) VALUES ('Antique appraisal', '2024-04-15 14:00', 2, 2);",
                "INSERT INTO auctionAttendance (type, time, clientId, expertId) VALUES ('Auction attendance', '2024-04-20 18:00', 1, 1);"
            };
            for (String sql : services) {
                stmt.executeUpdate(sql);
            }

            // Insert Locations
            String[] locations = {
                "INSERT INTO Location (address, city) VALUES ('123 Auction St.', 'New York');",
                "INSERT INTO Location (address, city) VALUES ('456 Antique Rd.', 'London');"
            };
            for (String sql : locations) {
                stmt.executeUpdate(sql);
            }

            // Insert Schedule for Events
            String[] schedules = {
                "INSERT INTO Schedule (date, time) VALUES ('2024-04-10', '10:00');",
                "INSERT INTO Schedule (date, time) VALUES ('2024-04-15', '14:00');"
            };
            for (String sql : schedules) {
                stmt.executeUpdate(sql);
            }

            // Insert EventSchedule (linking locations, schedules, and auctions)
            String[] eventSchedules = {
                "INSERT INTO EventSchedule (locationId, scheduleId, auctionId) VALUES (1, 1, 1);",
                "INSERT INTO EventSchedule (locationId, scheduleId, auctionId) VALUES (2, 2, 2);"
            };
            for (String sql : eventSchedules) {
                stmt.executeUpdate(sql);
            }

            // Insert Viewings for Auctions
            String[] viewings = {
                "INSERT INTO viewing (auctionHouseId) VALUES (1);",
                "INSERT INTO viewing (auctionHouseId) VALUES (2);"
            };
            for (String sql : viewings) {
                stmt.executeUpdate(sql);
            }

            // Update normalAuction with viewingIds
            stmt.executeUpdate("UPDATE normalAuction SET viewingId = 1 WHERE auctionID = 1;");
            stmt.executeUpdate("UPDATE normalAuction SET viewingId = 2 WHERE auctionID = 2;");

            System.out.println("Data inserted successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
