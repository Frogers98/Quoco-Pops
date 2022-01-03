package borrow;

import akka.actor.*;
import messages.borrow.*;

import java.sql.*;
import java.util.ArrayList;

import org.joda.time.DateTime;

public class LoanService extends AbstractActor {
    static ActorSystem loanSystem;
    private final static String dBURL = "jdbc:mysql://test.c2qef7oxk1tu.eu-west-1.rds.amazonaws.com:3306/loans";
    private final static String dbUsername = "admin";
    private final static String dbPassword = "Passw0rd1";
    private static ArrayList<String> libraryNames = new ArrayList<>();

    public static void main(String[] args){
        // Set up actor system, this method should be called initially before anything else in the class
        // loanSystem = ActorSystem.create();
        // // Create an actor for this ActorSystem for this class
        // ActorRef ref = loanSystem.actorOf(Props.create(LoanService.class), "loan");

        // Register this with the broker
        // ActorSelection selection =
        // loanSystem.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
        // selection.tell("registerLoan", ref);
        //TEMPORARY - add tallaght library to libraryNames, library names should really be stored in a database or somewhere else
        libraryNames.add("tallaght_library");

        System.out.println("HELLO I WORK");

        try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {

            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
        .match(LoanAddition.class,
        LoanAddition -> {
                try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
                String table = "tallaght_library_loans";
                String SQL = "INSERT INTO " + table + " (loanID, bookID, memberID, loanDate, returnDate)" +
                " VALUES (?,?,?,?,?)";
                PreparedStatement statement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, LoanAddition.getUserID());
                statement.setInt(2, LoanAddition.getUserID());
                statement.setInt(3, LoanAddition.getBookID());

                DateTime borrowDate = new DateTime();
                statement.setString(4, borrowDate.toString());
                DateTime returnDate = new DateTime().plusDays(7);
                statement.setString(5, returnDate.toString());
                // Execute the sql query (returns the rows affected by the query
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                getSender().tell("bookAdditionSuccess", getSelf());

            }
            
            } catch(SQLException e) {
            
                e.printStackTrace();
            
            }
        })
        .match(RetrieveLoan.class,
        RetrieveLoan -> {
            try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {

                String table = "tallaght_library_loans";
                // can input anything to do with the loan to return the loan information
                    String SQL = "SELECT * FROM " + table + " WHERE loanID =?";
                    PreparedStatement statement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
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
                                res.getString("returnDate")
                        );
                        System.out.println("loan from " + table + ". Return Date: " + res.getString("returnDate"));
                        getSender().tell(response, getSelf());

                    }
                //END FOR LOOP}
            } catch (SQLException e) {
                e.printStackTrace();;
            }


        })
        .build();
    }
}
