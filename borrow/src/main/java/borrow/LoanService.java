package borrow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Days;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import messages.OperationStatusResponse;
import messages.borrow.CalculateFinesRequest;
import messages.borrow.CalculateFinesResponse;
import messages.borrow.LoanBookRequest;
import messages.borrow.RetrieveLoan;
import messages.borrow.ReturnBookRequest;
import messages.borrow.SearchResponse;

public class LoanService extends AbstractActor {
    static ActorSystem loanSystem;
    private final static String dBURL =
    "jdbc:mysql://test.c2qef7oxk1tu.eu-west-1.rds.amazonaws.com:3306/loans";
    private final static String dbUsername = "admin";
    private final static String dbPassword = "Passw0rd1";

    // private final static String dBURL = "jdbc:mysql://localhost:3306/ds_project";
    // private final static String dbUsername = "root";
    // private final static String dbPassword = "Passw0rd1";

    public static void main(String[] args) {
        // Set up ActorSystem, this method should be called initially before anything
        // else in the class
        loanSystem = ActorSystem.create();

        // Create an Actor for this ActorSystem
        ActorRef ref = loanSystem.actorOf(Props.create(LoanService.class), "loan");

        // Register Actor with the Broker
        ActorSelection selection = loanSystem.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
        selection.tell("registerLoan", ref);

        // Open a database connection
        try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword);
                // (int loanID, int userID, int bookID, String loanDate, String returnDate, int
                // finesOwed,String libraryRef)
                Statement stmt = conn.createStatement();) {
            String sql = "CREATE TABLE IF NOT EXISTS LOANS " +
                    "(loan_id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " book_id INTEGER NOT NULL, " +
                    " member_id INTEGER NOT NULL, " +
                    " loan_date VARCHAR(255), " +
                    " return_date VARCHAR(255), " +
                    " actual_return_date VARCHAR(255), " +
                    " library_ref VARCHAR(255), " +
                    " PRIMARY KEY ( loan_id ))";
            stmt.executeUpdate(sql);
            System.out.println("Created LOANS in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword);
                Statement stmt = conn.createStatement();) {
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

                                // Check if registered member
                                String SQL1 = "SELECT * FROM valid WHERE id=1";

                                PreparedStatement statement1 = conn.prepareStatement(SQL1,
                                        Statement.RETURN_GENERATED_KEYS);
                                ResultSet res1 = statement1.executeQuery();

                                if (res1.next()) {
                                    String SQL = "INSERT INTO LOANS (loan_id, book_id, member_id, loan_date, return_date, actual_return_date, library_ref)"
                                            +
                                            " VALUES (?,?,?,?,?,?,?)";
                                    PreparedStatement statement = conn.prepareStatement(SQL,
                                            Statement.RETURN_GENERATED_KEYS);
                                    statement.setInt(1, LoanAddition.getLoanID());
                                    statement.setInt(2, LoanAddition.getBookID());
                                    statement.setInt(3, LoanAddition.getUserID());

                                    DateTime borrowDate = new DateTime();
                                    System.out.println("helllloooooo" + borrowDate);
                                    statement.setString(4, borrowDate.toString());

                                    DateTime returnDate = new DateTime().plusDays(7);
                                    statement.setString(5, returnDate.toString());

                                    statement.setString(6, "");
                                    statement.setString(7, LoanAddition.getLibraryRef());

                                    // Execute the sql query (returns the rows affected by the query)
                                    int rowsAffected = statement.executeUpdate();

                                    if (rowsAffected > 0) {
                                        getSender().tell(new OperationStatusResponse(LoanAddition.getLibraryRef(),
                                                LoanAddition.getUserID(), "Loan successful"), getSelf());
                                    } else {
                                        getSender().tell(new OperationStatusResponse(LoanAddition.getLibraryRef(),
                                                LoanAddition.getUserID(), "Loan unsuccessful"), getSelf());
                                    }
                                } else {
                                    getSender().tell(new OperationStatusResponse(LoanAddition.getLibraryRef(),
                                            LoanAddition.getUserID(), "Invalid member"), getSelf());
                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        })
                .match(RetrieveLoan.class,
                        RetrieveLoan -> {
                            try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {

                                // Use loanID to retrieve loan information
                                String SQL = "SELECT * FROM LOANS WHERE loan_id =?";
                                PreparedStatement statement = conn.prepareStatement(SQL,
                                        Statement.RETURN_GENERATED_KEYS);
                                statement.setInt(1, RetrieveLoan.getLoanID());
                                ResultSet res = statement.executeQuery();
                                // Create a SearchResponse object with the result and send it back to the Broker
                                // (loanID, bookID, memberID, loanDate, returnDate)
                                while (res.next()) {
                                    SearchResponse response = new SearchResponse(
                                            res.getInt("loan_id"),
                                            res.getInt("book_id"),
                                            res.getInt("member_id"),
                                            res.getString("loan_date"),
                                            res.getString("return_date"),
                                            res.getString("actual_return_date"),
                                            res.getString("library_ref"));
                                    // System.out.println(
                                    //         "loan from LOANS. Return Date: " + res.getString("return_date"));
                                    getSender().tell(response, getSelf());
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        })
                .match(CalculateFinesRequest.class,
                        Request -> {
                            ArrayList<Integer> loanLengths = new ArrayList<Integer>();

                            int rate1 = 15;
                            int rate2 = 30;
                            int rate3 = 60;

                            int totalFine = 0;

                            try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
                                String query = "SELECT member_id, loan_date, actual_return_date FROM LOANS WHERE member_id=?";

                                PreparedStatement statement = conn.prepareStatement(query,
                                        Statement.RETURN_GENERATED_KEYS);
                                statement.setInt(1, Request.getMemberId());
                                ResultSet res = statement.executeQuery();

                                while (res.next()) {
                                    DateTime loanDate = DateTime.parse(res.getString("loan_date"));
                                    DateTime untilDate;
                                    if (res.getString("actual_return_date").isEmpty()) {
                                        untilDate = new DateTime();
                                    } else {
                                        untilDate = DateTime.parse(res.getString("actual_return_date"));
                                    }

                                    // int days = (int) ChronoUnit.DAYS.between(loanDate, untilDate);
                                    int days = Days.daysBetween(loanDate.toLocalDate(), untilDate.toLocalDate())
                                            .getDays();

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
                                            new CalculateFinesResponse(Request.getLibraryRef(), Request.getMemberId(),
                                                    1000),
                                            getSelf());
                                } else {
                                    getSender().tell(new CalculateFinesResponse(Request.getLibraryRef(),
                                            Request.getMemberId(), totalFine), getSelf());
                                }
                            }
                        })
                .match(ReturnBookRequest.class,
                        Request -> {
                            try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
                                DateTime currentDateTime = new DateTime();

                                String SQL = "UPDATE LOANS SET actual_return_date=" + currentDateTime
                                        + " WHERE loan_id=" + Request.getLoanId();
                                PreparedStatement statement = conn.prepareStatement(SQL,
                                        Statement.RETURN_GENERATED_KEYS);

                                int rowsAffected = statement.executeUpdate();

                                if (rowsAffected > 0) {
                                    getSender().tell(new OperationStatusResponse(Request.getLibraryRef(),
                                            Request.getMemberId(), "Book returned successfully"), getSelf());
                                } else {
                                    getSender().tell(new OperationStatusResponse(Request.getLibraryRef(),
                                            Request.getMemberId(), "Operation unsuccessful"), getSelf());
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        })
                .build();
    }
}
