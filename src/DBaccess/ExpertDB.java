package DBaccess;

import AuctionSystem.Expert;
import Database.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpertDB {

    public static Expert getExpertByCredentials(String email, String password) throws Exception {
        Connection conn = DatabaseManager.getConnection();
        String query = "SELECT * FROM Expert WHERE email = ? AND password = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        Expert expert = null;
        if (rs.next()) {
            expert = new Expert(
                    rs.getString("name"),
                    rs.getString("email"),
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
        String sql = "INSERT INTO Expert (name, email, password, contact, licenseNum, specialty) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, expert.getName());
        stmt.setString(2, expert.getEmail());
        stmt.setString(3, expert.getPassword());
        stmt.setString(4, expert.getContact());
        stmt.setInt(5, expert.getLicenceNum());
        stmt.setString(6, expert.getSpecialty());

        int rows = stmt.executeUpdate();
        stmt.close();
        return rows > 0;
    }

    public static int getExpertIdByLicenseNum(int licenseNum) {
        int expertId = -1;
        try {
            Connection conn = DatabaseManager.getConnection();
            String sql = "SELECT expertId FROM Expert WHERE licenseNum = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, licenseNum);  // cleaner than using String.valueOf
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                expertId = rs.getInt("expertId");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error fetching expert ID: " + e.getMessage());
        }

        return expertId;
    }
}
