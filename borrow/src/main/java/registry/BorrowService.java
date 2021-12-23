package borrow;

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

public class BorrowService {

    private final static String dBURL = "jdbc:mysql://localhost:3306/ds_project";
    private final static String dbUsername = "borrow";
    private final static String dbPassword = "Passw0rd1";

    public void registerBorrow(Member info, BorrowAddition borrow ) throws SQLException {
        try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
            String table = "tallaght_library_members";
            String query = "INSERT INTO " + table + " (UserID, BookID, quantity, dateOfBorrow, DateOfReturn) VALUES ?,?,?,?,?";
            PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, info.getId());
            statement.setInt(2, borrow.getBookId());
            statement.setInt(3, borrow.getNumCopies());
            Calendar calendar = Calendar.getInstance();
            Calendar returnDate = calendar.add(Calendar.DATE, 12);
            statement.setDate(4, java.sql.Date.valueOf(calendar.getTime()));
            statement.setDate(5, java.sql.Date.valueOf(returnDate.getTime()));
            ResultSet res = statement.executeQuery();

            System.out.println(res + " borrow added");
            System.out.println(calendar.getTime());// print today's date
            System.out.println("Return Date: " + returnDate.getTime()+ " \n today's Date: "+ calendar.getTime());
            conn.close();
        }
        
    }

}
