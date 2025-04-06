package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database manager using singleton pattern to provide global access to connection
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:" + new java.io.File("src/Database/auction_system.db").getAbsolutePath();
    private static Connection connection = null;
    
    // Private constructor to prevent instantiation
    private DatabaseManager() {}
    
    /**
     * Get the database connection. Creates a new one if none exists.
     * @return The database connection
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
            try (var stmt = connection.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
        }
        return connection;
    }
    
    /**
     * Close the database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}