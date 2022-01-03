package borrow;

import akka.actor.*;
import messages.borrow.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import org.joda.time.DateTime;

public class LoanService extends AbstractActor {
    static ActorSystem loanSystem;
    private final static String dBURL = "jdbc:mysql://test.c2qef7oxk1tu.eu-west-1.rds.amazonaws.com:3306/loans";
    private final static String dbUsername = "admin";
    private final static String dbPassword = "Passw0rd1";


    public static void main(String[] args) {
        // Set up actor system, this method should be called initially before anything
        // else in the class
        loanSystem = ActorSystem.create();
        // Create an actor for this ActorSystem for this class
        ActorRef ref = loanSystem.actorOf(Props.create(LoanService.class), "loan");

        // Register this with the broker
        ActorSelection selection = loanSystem.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
        selection.tell("registerLoan", ref);
        // Open a connection
        //( int loanID, int userID, int bookID, String loanDate, String returnDate, int finesOwed,String libraryRef)
        try(Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword);
        Statement stmt = conn.createStatement();
        ) 
        {		      
            String sql = "CREATE TABLE IF NOT EXISTS LOANS " +
                    "(loan_id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " book_id INTEGER NOT NULL, " + 
                    " member_id INTEGER NOT NULL, " + 
                    " loan_date VARCHAR(255), " +
                    " return_date VARCHAR(255), " +
                    " fines_owed INTEGER, " + 
                    " library_ref VARCHAR(255), " + 
                    " PRIMARY KEY ( loan_id ))"; 
            stmt.executeUpdate(sql);
            System.out.println("Created LOANS in given database...");   	  
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        try(Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword);
        Statement stmt = conn.createStatement();
        ) 
        {		      
            String sql = "CREATE TABLE IF NOT EXISTS VALID " +
                    "(member_id INTEGER NOT NULL, " +
                    " PRIMARY KEY ( member_id ))"; 
            stmt.executeUpdate(sql);
            System.out.println("Created VALID in given database...");   	  
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(LoanBookRequest.class,
                        LoanAddition -> {
                            try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
                                String table = "LOANS";
                                String SQL = "INSERT INTO " + table
                                        + " (loan_id, book_id, member_id, loan_date, return_date, fine, library_ref)" +
                                        " VALUES (?,?,?,?,?,?)";
                                PreparedStatement statement = conn.prepareStatement(SQL,
                                        Statement.RETURN_GENERATED_KEYS);
                                statement.setInt(1, LoanAddition.getUserID());
                                statement.setInt(2, LoanAddition.getUserID());
                                statement.setInt(3, LoanAddition.getBookID());

                                DateTime borrowDate = new DateTime();
                                statement.setString(4, borrowDate.toString());
                                DateTime returnDate = new DateTime().plusDays(7);
                                statement.setString(5, returnDate.toString());
                                statement.setInt(6, LoanAddition.getFinesOwed());
                                statement.setString(7, LoanAddition.getLibraryRef());
                                //getLibraryRef()
                                // Execute the sql query (returns the rows affected by the query
                                int rowsAffected = statement.executeUpdate();

                                if (rowsAffected > 0) {
                                    getSender().tell("bookAdditionSuccess", getSelf());

                                }

                            } catch (SQLException e) {

                                e.printStackTrace();

                            }
                        })
                .match(RetrieveLoan.class,
                        RetrieveLoan -> {
                            try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {

                                String table = "tallaght_library_loans";
                                // can input anything to do with the loan to return the loan information
                                String SQL = "SELECT * FROM " + table + " WHERE loanID =?";
                                PreparedStatement statement = conn.prepareStatement(SQL,
                                        Statement.RETURN_GENERATED_KEYS);
                                statement.setInt(1, RetrieveLoan.getLoanID());
                                ResultSet res = statement.executeQuery();
                                // Create a SearchResponse object with the result and send it back to the broker
                                // (loanID, bookID, memberID, loanDate, returnDate)
                                while (res.next()) {
                                    SearchResponse response = new SearchResponse(
                                            res.getInt("loanID"),
                                            res.getInt("bookID"),
                                            res.getInt("memberID"),
                                            res.getString("loanDate"),
                                            res.getString("returnDate"));
                                    System.out.println(
                                            "loan from " + table + ". Return Date: " + res.getString("returnDate"));
                                    getSender().tell(response, getSelf());

                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                                ;
                            }

                        })
                .match(CalculateFinesRequest.class,
                        Request -> {
                            ArrayList<Integer> loanLengths = new ArrayList<Integer>();

                            int rate1 = 15;
                            int rate2 = 30;
                            int rate3 = 60;

                            int totalFine = 0;
                            LocalTime currentTime = LocalTime.now();

                            try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
                                String table = "tallaght_library_loans";
                                String query = "SELECT user_id, loan_date FROM " + table + " WHERE id = ?";

                                PreparedStatement statement = conn.prepareStatement(query,
                                        Statement.RETURN_GENERATED_KEYS);
                                statement.setInt(1, Request.getId());
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
                                    getSender().tell(
                                            new CalculateFinesResponse(Request.getLibraryRef(), Request.getId(), 1000),
                                            getSelf());
                                } else {
                                    getSender().tell(new CalculateFinesResponse(Request.getLibraryRef(),
                                            Request.getId(), totalFine), getSelf());
                                }
                            }
                        })
                .match(ReturnBookRequest.class,
                        msg -> {
                            try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
                                String SQL = "DELETE FROM loans WHERE library_ref=\"" + msg.getLibraryRef()
                                        + "\" AND member_id=" + msg.getMemberId();
                                PreparedStatement statement = conn.prepareStatement(SQL,
                                        Statement.RETURN_GENERATED_KEYS);

                                int rowsAffected = statement.executeUpdate();

                                if (rowsAffected > 0) {
                                    getSender().tell(rowsAffected, getSelf());
                                }
                            }
                        })
                .build();
    }
}
