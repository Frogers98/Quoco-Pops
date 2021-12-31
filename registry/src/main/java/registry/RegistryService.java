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
            String query = "INSERT INTO " + table
                    + " (name, gender, year_of_birth, password, home_library, email, phone_no) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, info.getName());
            statement.setString(2, String.valueOf(info.getGender()));
            statement.setInt(3, info.getYearOfBirth());
            statement.setString(4, info.getPassword());
            statement.setString(5, info.getHomeLibrary());
            statement.setString(6, info.getEmail());
            statement.setString(7, info.getPhoneNumber());

            int rowsAffected = statement.executeUpdate();

            System.out.println(rowsAffected + " member(s) added");
            conn.close();
        }
    }

    public void deleteMember(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
            String table = "tallaght_library_members";
            String query = "DELETE FROM " + table + " WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " member(s) deleted");
            conn.close();

        }
    }

    public Member retrieveMemberDetails(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
            String table = "tallaght_library_members";
            String query = "SELECT * FROM " + table + " WHERE id=" + id;

            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.absolute(1);

            Member member = new Member(resultSet.getString("name"), resultSet.getString("gender").charAt(0),
            resultSet.getInt("year_of_birth"), resultSet.getString("password"), resultSet.getInt("id"),
            resultSet.getString("home_library"), resultSet.getString("phone_no"), resultSet.getString("email"));

            System.out.println("Member with id " + id + " retrieved");

            conn.close();
            return member;
        }
    }

    public Boolean updatePassword(int id, String oldPassword, String newPassword) throws SQLException {
        try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
            String table = "tallaght_library_members";
            String query = "SELECT password FROM " + table + " WHERE id =" + id;

            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.absolute(1);

            if (resultSet.getString("password").equals(oldPassword)) {
                String query2 = "UPDATE " + table + " SET password = ? WHERE id = ?";
                PreparedStatement statement2 = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
                statement2.setString(1, newPassword);
                statement2.setInt(2, id);
                int rowsAffected = statement2.executeUpdate();
                Boolean success = (rowsAffected > 0) ? true : false;
                return success;
            } else {
                return false;
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
            String query = "SELECT * FROM " + table + " WHERE id = ?";
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
