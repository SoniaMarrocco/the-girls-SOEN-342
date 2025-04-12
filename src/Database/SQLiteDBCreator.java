package Database;

import java.sql.Connection;
import java.sql.DriverManager;
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

            String[] createTables = {
                    "CREATE TABLE IF NOT EXISTS Administrator (adminID INTEGER PRIMARY KEY, name TEXT);",
                    "CREATE TABLE IF NOT EXISTS Client (clientId INTEGER PRIMARY KEY, name TEXT, email TEXT, password TEXT, affiliation TEXT, contactInfo TEXT, description TEXT);",
                    "CREATE TABLE IF NOT EXISTS Client_Auction (client INTEGER, auctionID INTEGER, FOREIGN KEY(client) REFERENCES Client(clientId), FOREIGN KEY(auctionID) REFERENCES normalAuction(auctionID));",
                    "CREATE TABLE IF NOT EXISTS normalAuction (auctionID INTEGER PRIMARY KEY, speciality TEXT, auctionTitle TEXT, auctionHouseId INTEGER, viewingId INTEGER, FOREIGN KEY(auctionHouseId) REFERENCES AuctionHouse(auctionHouseId), FOREIGN KEY(viewingId) REFERENCES viewing(viewingId));",
                    "CREATE TABLE IF NOT EXISTS onlineAuction (auctionID INTEGER PRIMARY KEY, speciality TEXT, auctionTitle TEXT, auctionHouseId INTEGER, FOREIGN KEY(auctionHouseId) REFERENCES AuctionHouse(auctionHouseId));",
                    "CREATE TABLE IF NOT EXISTS Client_Viewing (clientId INTEGER, viewingId INTEGER, FOREIGN KEY(clientId) REFERENCES Client(clientId), FOREIGN KEY(viewingId) REFERENCES viewing(viewingId));",
                    "CREATE TABLE IF NOT EXISTS viewing (viewingId INTEGER PRIMARY KEY, auctionHouseId INTEGER, FOREIGN KEY(auctionHouseId) REFERENCES AuctionHouse(auctionHouseId));",
                    "CREATE TABLE IF NOT EXISTS AuctionHouse (auctionHouseId INTEGER PRIMARY KEY, name TEXT);",
                    "CREATE TABLE IF NOT EXISTS Objects (objectsId INTEGER PRIMARY KEY, name TEXT, description TEXT, available BOOLEAN, auctionHouseID INTEGER, normalAuctionId INTEGER, onlineAuctionId INTEGER, FOREIGN KEY(auctionHouseID) REFERENCES AuctionHouse(auctionHouseId), FOREIGN KEY(normalAuctionId) REFERENCES normalAuction(auctionId), FOREIGN KEY(onlineAuctionId) REFERENCES onlineAuction(auctionID));",
                    "CREATE TABLE IF NOT EXISTS Expert (expertId INTEGER PRIMARY KEY, name TEXT, contact TEXT, licenseNum TEXT, specialty TEXT, email TEXT UNIQUE, password TEXT);",
                    "CREATE TABLE IF NOT EXISTS objectAdvising (serviceID INTEGER PRIMARY KEY, type TEXT, date TEXT, startTime TEXT, endTime TEXT, clientId INTEGER, expertId INTEGER, FOREIGN KEY(clientId) REFERENCES Client(clientId), FOREIGN KEY(expertId) REFERENCES Expert(expertId));",
                    "CREATE TABLE IF NOT EXISTS consulting (consultingID INTEGER PRIMARY KEY, type TEXT, date TEXT, startTime TEXT, endTime TEXT, clientId INTEGER, expertId INTEGER, FOREIGN KEY(clientId) REFERENCES Client(clientId), FOREIGN KEY(expertId) REFERENCES Expert(expertId));",
                    "CREATE TABLE IF NOT EXISTS auctionAttendance (attendanceID INTEGER PRIMARY KEY, type TEXT, date TEXT, startTime TEXT, endTime TEXT, clientId INTEGER, expertId INTEGER, FOREIGN KEY(clientId) REFERENCES Client(clientId), FOREIGN KEY(expertId) REFERENCES Expert(expertId));",
                    "CREATE TABLE IF NOT EXISTS EventSchedule (eventScheduleID INTEGER PRIMARY KEY, address TEXT, city TEXT, date TEXT, startTime TEXT, endTime TEXT, auctionId INTEGER, FOREIGN KEY(auctionId) REFERENCES normalAuction(auctionID));"
            };

            for (String sql : createTables) {
                stmt.execute(sql);
            }

            String[] auctionHouses = {
                    "INSERT INTO AuctionHouse (name) VALUES ('Sothebys');",
                    "INSERT INTO AuctionHouse (name) VALUES ('Christies');"
            };
            for (String sql : auctionHouses) stmt.executeUpdate(sql);

            String[] auctions = {
                    "INSERT INTO normalAuction (speciality, auctionTitle, auctionHouseId) VALUES ('Art', 'Modern Art Auction', 1);",
                    "INSERT INTO normalAuction (speciality, auctionTitle, auctionHouseId) VALUES ('Antiques', 'Antique Auction', 2);"
            };
            for (String sql : auctions) stmt.executeUpdate(sql);

            String[] onlineAuctions = {
                    "INSERT INTO onlineAuction (speciality, auctionTitle, auctionHouseId) VALUES ('Jewelry', 'Fine Jewelry Online Auction', 1);",
                    "INSERT INTO onlineAuction (speciality, auctionTitle, auctionHouseId) VALUES ('Watches', 'Luxury Watches Online Auction', 2);"
            };
            for (String sql : onlineAuctions) stmt.executeUpdate(sql);

            String[] clients = {
                    "INSERT INTO Client (name, email, password, affiliation, contactInfo, description) VALUES ('alice', 'alice@example.com', 'password1', 'Gallery A', '123-456-7890', 'Art collector');",
                    "INSERT INTO Client (name, email, password, affiliation, contactInfo, description) VALUES ('bob', 'bob@example.com', 'password2', 'Museum B', '987-654-3210', 'Antique enthusiast');",
                    "INSERT INTO Client (name, email, password, affiliation, contactInfo, description) VALUES ('carol', 'carol@example.com', 'password3', 'University C', '555-555-5555', 'Researcher in Fine Arts');"
            };
            for (String sql : clients) stmt.executeUpdate(sql);

            String[] experts = {
                    "INSERT INTO Expert (name, contact, licenseNum, specialty, email, password) VALUES ('John Doe', 'john.doe@example.com', 'EX12345', 'Art Expert', 'john@example.com', 'password123');",
                    "INSERT INTO Expert (name, contact, licenseNum, specialty, email, password) VALUES ('Jane Smith', 'jane.smith@example.com', 'EX67890', 'Antique Expert', 'jane@example.com', 'jane');",
                    "INSERT INTO Expert (name, contact, licenseNum, specialty, email, password) VALUES ('Sophie Brown', 'sophie.brown@example.com', 'EX00001', 'Furniture Expert', 'sophie@example.com', 'securepass');"
            };
            for (String sql : experts) stmt.executeUpdate(sql);

            String[] services = {
                    "INSERT INTO objectAdvising (type, date, startTime, endTime, clientId, expertId) VALUES ('Art valuation', '2024-04-10', '10:00', '12:00', 1, 1);",
                    "INSERT INTO objectAdvising (type, date, startTime, endTime, clientId, expertId) VALUES ('Sculpture guidance', '2024-04-12', '13:00', '14:30', 3, 1);",
                    "INSERT INTO consulting (type, date, startTime, endTime, clientId, expertId) VALUES ('Antique appraisal', '2024-04-15', '14:00', '16:30', 2, 2);",
                    "INSERT INTO consulting (type, date, startTime, endTime, clientId, expertId) VALUES ('Painting consultation', '2024-04-18', '09:00', '11:00', 1, 1);",
                    "INSERT INTO auctionAttendance (type, date, startTime, endTime, clientId, expertId) VALUES ('Auction attendance', '2024-04-20', '18:00', '21:00', 1, 1);",
                    "INSERT INTO auctionAttendance (type, date, startTime, endTime, clientId, expertId) VALUES ('Antique event attendance', '2024-04-25', '16:00', '18:00', 2, 2);"
            };
            for (String sql : services) stmt.executeUpdate(sql);

            String[] eventSchedules = {
                    "INSERT INTO EventSchedule (address, city, date, startTime, endTime, auctionId) VALUES ('123 Auction St.', 'New York', '2024-04-10', '10:00', '12:00', 1);",
                    "INSERT INTO EventSchedule (address, city, date, startTime, endTime, auctionId) VALUES ('456 Antique Rd.', 'London', '2024-04-15', '14:00', '15:00', 2);"
            };
            for (String sql : eventSchedules) stmt.executeUpdate(sql);

            String[] viewings = {
                    "INSERT INTO viewing (auctionHouseId) VALUES (1);",
                    "INSERT INTO viewing (auctionHouseId) VALUES (2);"
            };
            for (String sql : viewings) stmt.executeUpdate(sql);

            stmt.executeUpdate("UPDATE normalAuction SET viewingId = 1 WHERE auctionID = 1;");
            stmt.executeUpdate("UPDATE normalAuction SET viewingId = 2 WHERE auctionID = 2;");

            String[] normalAuctionObjects = {
                    "INSERT INTO Objects (name, description, available, auctionHouseID, normalAuctionId, onlineAuctionId) VALUES ('Picasso Sketch', 'Picasso sketch, 1956, charcoal on paper', TRUE, 1, 1, NULL);",
                    "INSERT INTO Objects (name, description, available, auctionHouseID, normalAuctionId, onlineAuctionId) VALUES ('Warhol Print', 'Warhol silkscreen print, Campbell''s Soup series', TRUE, 1, 1, NULL);",
                    "INSERT INTO Objects (name, description, available, auctionHouseID, normalAuctionId, onlineAuctionId) VALUES ('Basquiat Painting', 'Basquiat mixed media painting, urban motifs', TRUE, 1, 1, NULL);",
                    "INSERT INTO Objects (name, description, available, auctionHouseID, normalAuctionId, onlineAuctionId) VALUES ('French Writing Desk', '18th century French writing desk, mahogany', TRUE, 2, 2, NULL);",
                    "INSERT INTO Objects (name, description, available, auctionHouseID, normalAuctionId, onlineAuctionId) VALUES ('Victorian Tea Set', 'Victorian silver tea set, complete, hallmarked 1872', TRUE, 2, 2, NULL);",
                    "INSERT INTO Objects (name, description, available, auctionHouseID, normalAuctionId, onlineAuctionId) VALUES ('Chagall Lithograph', 'Rare Marc Chagall print, colorful composition', TRUE, 1, 1, NULL);"
            };
            for (String sql : normalAuctionObjects) stmt.executeUpdate(sql);

            String[] onlineAuctionObjects = {
                    "INSERT INTO Objects (name, description, available, auctionHouseID, normalAuctionId, onlineAuctionId) VALUES ('Diamond Necklace', 'Tiffany diamond necklace, 3.5 carats, platinum setting', TRUE, 1, NULL, 1);",
                    "INSERT INTO Objects (name, description, available, auctionHouseID, normalAuctionId, onlineAuctionId) VALUES ('Rolex Submariner', 'Rolex Submariner, vintage 1985, box and papers included', TRUE, 2, NULL, 2);"
            };
            for (String sql : onlineAuctionObjects) stmt.executeUpdate(sql);

        } catch (SQLException e) {
        }
    }
}