package borrow;

import akka.actor.*;
import messages.borrow.*;

import java.sql.*;
import java.util.ArrayList;

import org.joda.time.DateTime;

public class LoanService extends AbstractActor {
    static ActorSystem loanSystem;
    private final static String dBURL = "jdbc:mysql://localhost:3306/ds_project";
    private final static String dbUsername = "borrow";
    private final static String dbPassword = "Passw0rd1";
    private static ArrayList<String> libraryNames = new ArrayList<>();


    public static void main(String[] args) {
        // Set up actor system, this method should be called initially before anything else in the class
        loanSystem = ActorSystem.create();
        // Create an actor for this ActorSystem for this class
        ActorRef ref = loanSystem.actorOf(Props.create(LoanService.class), "loan");

        // Register this with the broker
        ActorSelection selection =
        loanSystem.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
        selection.tell("registerLoan", ref);
        //TEMPORARY - add tallaght library to libraryNames, library names should really be stored in a database or somewhere else
        libraryNames.add("tallaght_library");

    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
        .match(LoanAddition.class,
        LoanAddition -> {
                // Get the library this book is being added to so we add it to the right table
                // String libraryName = LoanAddition.getLibraryName();

                // try with block to instantiate database stuff so it will close itself when finished
                try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
                String table = "loans";
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
                // if at least one row was affected send a string back to the sender to indicate success
                // this is only a temporary fix for the unit test as it waits for a string after sending a
                // test bookAddition to this service
                if (rowsAffected > 0) {
                getSender().tell("bookAdditionSuccess", getSelf());
                
            }
            
            } catch(SQLException e) {
            
                e.printStackTrace();
            
            }
        }).build();
    }
}
