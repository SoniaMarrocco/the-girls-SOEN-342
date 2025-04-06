import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSetup {
    
    public static void main(String[] args) {
        // Database file path - update this path as needed
        String url = "jdbc:sqlite:auction_system.db";
        
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to the database. Starting setup...");
                createTablesIfNotExist(conn);
                System.out.println("Database setup completed successfully.");
            }
        } catch (Exception e) {
            System.err.println("Database setup failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void createTablesIfNotExist(Connection conn) throws Exception {
        // Enable foreign keys
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
            
            // Get existing tables
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, "%", new String[] {"TABLE"});
            
            List<String> existingTables = new ArrayList<>();
            while (rs.next()) {
                existingTables.add(rs.getString("TABLE_NAME").toLowerCase());
            }
            
            // Create Administrator table if it doesn't exist
            if (!existingTables.contains("administrator")) {
                stmt.execute("CREATE TABLE Administrator (" +
                             "adminID INTEGER PRIMARY KEY," +
                             "name TEXT NOT NULL" +
                             ");");
                System.out.println("Created Administrator table");
            }
            
            // Create AuctionHouse table if it doesn't exist
            if (!existingTables.contains("auctionhouse")) {
                stmt.execute("CREATE TABLE AuctionHouse (" +
                             "auctionHouseId INTEGER PRIMARY KEY," +
                             "name TEXT NOT NULL" +
                             ");");
                System.out.println("Created AuctionHouse table");
            }
            
            // Create Expert table if it doesn't exist
            if (!existingTables.contains("expert")) {
                stmt.execute("CREATE TABLE Expert (" +
                             "expertId INTEGER PRIMARY KEY," +
                             "name TEXT NOT NULL," +
                             "contact TEXT," +
                             "licenseNum TEXT," +
                             "speciality TEXT" +
                             ");");
                System.out.println("Created Expert table");
            }
            
            // Create Location table if it doesn't exist
            if (!existingTables.contains("location")) {
                stmt.execute("CREATE TABLE Location (" +
                             "locationId INTEGER PRIMARY KEY," +
                             "address TEXT," +
                             "city TEXT" +
                             ");");
                System.out.println("Created Location table");
            }
            
            // Create Schedule table if it doesn't exist
            if (!existingTables.contains("schedule")) {
                stmt.execute("CREATE TABLE Schedule (" +
                             "scheduleId INTEGER PRIMARY KEY," +
                             "date TEXT," +
                             "time TEXT" +
                             ");");
                System.out.println("Created Schedule table");
            }
            
            // Create Client table if it doesn't exist
            if (!existingTables.contains("client")) {
                stmt.execute("CREATE TABLE Client (" +
                             "clientId INTEGER PRIMARY KEY," +
                             "email TEXT NOT NULL," +
                             "password TEXT NOT NULL," +
                             "affiliation TEXT," +
                             "contactInfo TEXT," +
                             "description TEXT" +
                             ");");
                System.out.println("Created Client table");
            }
            
            // Create viewing table if it doesn't exist
            if (!existingTables.contains("viewing")) {
                stmt.execute("CREATE TABLE viewing (" +
                             "viewingId INTEGER PRIMARY KEY," +
                             "auctionHouseId INTEGER," +
                             "FOREIGN KEY (auctionHouseId) REFERENCES AuctionHouse(auctionHouseId)" +
                             ");");
                System.out.println("Created viewing table");
            }
            
            // Create normalAuction table if it doesn't exist
            if (!existingTables.contains("normalauction")) {
                stmt.execute("CREATE TABLE normalAuction (" +
                             "auctionID INTEGER PRIMARY KEY," +
                             "speciality TEXT," +
                             "auctionTitle TEXT," +
                             "auctionHouseId INTEGER," +
                             "viewingId INTEGER," +
                             "FOREIGN KEY (auctionHouseId) REFERENCES AuctionHouse(auctionHouseId)," +
                             "FOREIGN KEY (viewingId) REFERENCES viewing(viewingId)" +
                             ");");
                System.out.println("Created normalAuction table");
            }
            
            // Create onlineAuction table if it doesn't exist
            if (!existingTables.contains("onlineauction")) {
                stmt.execute("CREATE TABLE onlineAuction (" +
                             "auctionID INTEGER PRIMARY KEY," +
                             "speciality TEXT," +
                             "auctionTitle TEXT," +
                             "auctionHouseId INTEGER," +
                             "FOREIGN KEY (auctionHouseId) REFERENCES AuctionHouse(auctionHouseId)" +
                             ");");
                System.out.println("Created onlineAuction table");
            }
            
            // Create Client_Viewing table if it doesn't exist
            if (!existingTables.contains("client_viewing")) {
                stmt.execute("CREATE TABLE Client_Viewing (" +
                             "clientId INTEGER," +
                             "viewingId INTEGER," +
                             "PRIMARY KEY (clientId, viewingId)," +
                             "FOREIGN KEY (clientId) REFERENCES Client(clientId)," +
                             "FOREIGN KEY (viewingId) REFERENCES viewing(viewingId)" +
                             ");");
                System.out.println("Created Client_Viewing table");
            }
            
            // Create Objects table if it doesn't exist
            if (!existingTables.contains("objects")) {
                stmt.execute("CREATE TABLE Objects (" +
                             "objectsId INTEGER PRIMARY KEY," +
                             "description TEXT," +
                             "available BOOLEAN," +
                             "auctionHouseID INTEGER," +
                             "auctionId INTEGER," +
                             "FOREIGN KEY (auctionHouseID) REFERENCES AuctionHouse(auctionHouseId)," +
                             "FOREIGN KEY (auctionId) REFERENCES normalAuction(auctionID)" +
                             ");");
                System.out.println("Created Objects table");
            }
            
            // Create Client_Auction table if it doesn't exist
            if (!existingTables.contains("client_auction")) {
                stmt.execute("CREATE TABLE Client_Auction (" +
                             "client INTEGER," +
                             "auctionID INTEGER," +
                             "PRIMARY KEY (client, auctionID)," +
                             "FOREIGN KEY (client) REFERENCES Client(clientId)," +
                             "FOREIGN KEY (auctionID) REFERENCES normalAuction(auctionID)" +
                             ");");
                System.out.println("Created Client_Auction table");
            }
            
            // Create objectAdvising table if it doesn't exist
            if (!existingTables.contains("objectadvising")) {
                stmt.execute("CREATE TABLE objectAdvising (" +
                             "serviceID INTEGER PRIMARY KEY," +
                             "type TEXT," +
                             "time TEXT," +
                             "clientId INTEGER," +
                             "expertId INTEGER," +
                             "FOREIGN KEY (clientId) REFERENCES Client(clientId)," +
                             "FOREIGN KEY (expertId) REFERENCES Expert(expertId)" +
                             ");");
                System.out.println("Created objectAdvising table");
            }
            
            // Create consulting table if it doesn't exist
            if (!existingTables.contains("consulting")) {
                stmt.execute("CREATE TABLE consulting (" +
                             "consultingID INTEGER PRIMARY KEY," +
                             "type TEXT," +
                             "time TEXT," +
                             "clientId INTEGER," +
                             "expertId INTEGER," +
                             "FOREIGN KEY (clientId) REFERENCES Client(clientId)," +
                             "FOREIGN KEY (expertId) REFERENCES Expert(expertId)" +
                             ");");
                System.out.println("Created consulting table");
            }
            
            // Create auctionAttendance table if it doesn't exist
            if (!existingTables.contains("auctionattendance")) {
                stmt.execute("CREATE TABLE auctionAttendance (" +
                             "attendanceID INTEGER PRIMARY KEY," +
                             "type TEXT," +
                             "time TEXT," +
                             "clientId INTEGER," +
                             "expertId INTEGER," +
                             "FOREIGN KEY (clientId) REFERENCES Client(clientId)," +
                             "FOREIGN KEY (expertId) REFERENCES Expert(expertId)" +
                             ");");
                System.out.println("Created auctionAttendance table");
            }
            
            // Create EventSchedule table if it doesn't exist
            if (!existingTables.contains("eventschedule")) {
                stmt.execute("CREATE TABLE EventSchedule (" +
                             "locationId INTEGER," +
                             "scheduleId INTEGER," +
                             "auctionId INTEGER," +
                             "PRIMARY KEY (locationId, scheduleId, auctionId)," +
                             "FOREIGN KEY (locationId) REFERENCES Location(locationId)," +
                             "FOREIGN KEY (scheduleId) REFERENCES Schedule(scheduleId)," +
                             "FOREIGN KEY (auctionId) REFERENCES normalAuction(auctionID)" +
                             ");");
                System.out.println("Created EventSchedule table");
            }
        }
    }
}