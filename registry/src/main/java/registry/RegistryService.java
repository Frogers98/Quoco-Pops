package registry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import core.Member;

public class RegistryService {

    private final static String dBURL = "jdbc:mysql://localhost:3306/ds_project";
    private final static String dbUsername = "root";
    private final static String dbPassword = "Passw0rd1";

    public void registerMember(Member info) throws SQLException {
        try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
            String table = "tallaght_library_members";
            String query = "INSERT INTO " + table + " (member_name, gender, year_of_birth) VALUES ?, ?, ?";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, info.getName());
            statement.setString(2, String.valueOf(info.getGender()));
            statement.setInt(3, info.getYearOfBirth());
            ResultSet res = statement.executeQuery();
            System.out.println(res + " member added");
            conn.close();

        }
    }

    public void deleteMember(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
            String table = "tallaght_library_members";
            String query = "DELETE FROM " + table + " WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            System.out.println(res + " member deleted");
            conn.close();

        }
    }

    public Member retrieveMemberDetails(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
            String table = "tallaght_library_members";
            String query = "SELECT FROM " + table + " WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            Member member = new Member(res.getString("member_name"), res.getString("gender").charAt(0),
                    res.getInt("year_of_birth"), res.getString("password"), res.getInt("id"),
                    res.getString("library_id"));
            conn.close();
            return member;
        }
    }

    public String updatePassword(int id, String oldPassword, String newPassword) throws SQLException {
        String returnMessage;
        try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
            String table = "tallaght_library_members";
            String query = "SELECT FROM " + table + " WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if (res.getString("password").equals(oldPassword)) {
                String query2 = "UPDATE " + table + " SET password = ? WHERE id = ?";
                PreparedStatement statement2 = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
                statement2.setString(1, newPassword);
                statement2.setInt(2, id);
                statement2.executeQuery();
                returnMessage = "Password updated successfully";
                conn.close();
                return returnMessage;

            } else {
                returnMessage = "Operation unsuccessful";
                conn.close();
                return returnMessage;
            }
        }

    }

    public int calculateFinesOwed(int id) throws SQLException {
        ArrayList<Integer> loanLengths = new ArrayList<Integer>();

        int rate1 = 15;
        int rate2 = 30;
        int rate3 = 60;

        int totalFine = 0;
        LocalTime currentTime = LocalTime.now();
        try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
            String table = "tallaght_library_fines";
            String query = "SELECT FROM " + table + " WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                LocalDateTime checkOutTime = LocalDateTime.parse(res.getString("checkout_date"));
                int days = (int) ChronoUnit.DAYS.between(checkOutTime, currentTime);

                if (days > 7) {
                    loanLengths.add(days);
                }
            }

            for (int loanLength : loanLengths) {
                if (loanLength <= 14) {
                    totalFine += (loanLength - 7) * rate1;
                }

                if ((loanLength > 14) && (loanLength <= 21)) {
                    totalFine += (7 * rate1) + (loanLength - 14) * rate2;
                }

                if ((loanLength > 21)) {
                    totalFine += (7 * rate1) + (7 * rate2) + (loanLength - 21) * rate3;
                }
            }

            if (totalFine > 1000) {
                return 1000;
            } else {
                return totalFine;
            }
        }

    }

}
