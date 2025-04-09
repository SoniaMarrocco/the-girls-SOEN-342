package DBaccess;

import AuctionSystem.Expert;
import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExpertDB {

    public static Expert getExpertByCredentials(String username, String password) throws Exception {
        Connection conn = DatabaseManager.getConnection();
        String query = "SELECT * FROM Expert WHERE username = ? AND password = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        Expert expert = null;
        if (rs.next()) {
            expert = new Expert(
                    rs.getString("name"),
                    rs.getString("contact"), // email is stored as contact
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("contact"),
                    Integer.parseInt(rs.getString("licenseNum")),
                    null, // specialty parsing optional
                    null  // objs
            );
        }

        rs.close();
        stmt.close();
        return expert;
    }

    public static boolean insertExpert(Expert expert) throws Exception {
        Connection conn = DatabaseManager.getConnection();
        String sql = "INSERT INTO Expert (name, contact, licenseNum, specialty, username, password) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, expert.getName());
        stmt.setString(2, expert.getContact());
        stmt.setString(3, String.valueOf(expert.getLicenceNum()));
        stmt.setString(4, "General"); // Simplified specialty for now
        stmt.setString(5, expert.getUsername());
        stmt.setString(6, expert.getPassword());

        int rows = stmt.executeUpdate();
        stmt.close();
        return rows > 0;
    }
}
