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
                    rs.getString("email"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("contact"),
                    rs.getInt("licenseNum"),
                    rs.getString("specialty")
            );
        }

        rs.close();
        stmt.close();
        return expert;
    }

    public static boolean insertExpert(Expert expert) throws Exception {
        Connection conn = DatabaseManager.getConnection();
        String sql = "INSERT INTO Expert (name, email, username, password, contact, licenseNum, specialty) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, expert.getName());
        stmt.setString(2, expert.getEmail());
        stmt.setString(3, expert.getUsername());
        stmt.setString(4, expert.getPassword());
        stmt.setString(5, expert.getContact());
        stmt.setInt(6, expert.getLicenceNum());
        stmt.setString(7, expert.getSpecialty());

        int rows = stmt.executeUpdate();
        stmt.close();
        return rows > 0;
    }
}
