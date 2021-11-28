package catalogue;

import akka.actor.*;
import messages.catalogue.*;

import java.sql.*;

public class CatalogueService extends AbstractActor {
    static ActorSystem catalogueSystem;
    private final static String dBURL = "jdbc:mysql://localhost:3306/ds_project";
    private final static String dbUsername = "root";
    private final static String dbPassword = "Passw0rd1";


    public static void main(String[] args) {
        // Set up actor system, this method should be called initially before anything else in the class
        catalogueSystem = ActorSystem.create();
        // Create an actor for this ActorSystem for this class
        ActorRef ref = catalogueSystem.actorOf(Props.create(CatalogueService.class), "catalogue");

        // Register this with the broker
        ActorSelection selection =
                catalogueSystem.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
        selection.tell("registerCatalogue", ref);


    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SearchRequest.class,
                        // Do a lookup in the database for the book by the book id and send a SearchResponse message
                        // back to the broker
                        searchRequest -> {


                        })
                .match(CatalogueAddition.class,
                        bookAddition -> {
                            // Get the library this book is being added to so we add it to the right table
                            String libraryName = bookAddition.getLibraryName();

                            // try with block to instantiate database stuff so it will close itself when finished
                            try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
                                String SQL = "INSERT into " + libraryName + " (book_id, book_title, book_author, available_copies, total_copies)" +
                                        " VALUES (?,?,?,?,?)";
                                PreparedStatement statement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                                statement.setInt(1, bookAddition.getBookID());
                                statement.setString(2, bookAddition.getBookTitle());
                                statement.setString(3, bookAddition.getBookAuthor());
                                statement.setInt(4, bookAddition.getNumCopies());
                                statement.setInt(5, bookAddition.getNumCopies());

                                statement.executeUpdate();
                            } catch(SQLException e) {
                                e.printStackTrace();
                            }
                        })

                .match(CatalogueRemoval.class,
                        bookRemoval -> {
                            // bookID is a unique value in the table so we can remove the row from
                            // the appropriate table with matching bookID
                            System.out.println("IN CATALOGUE REMOVAL");
                            String libraryName = bookRemoval.getLibraryName();

                            // try with block to instantiate database stuff so it will close itself when finished
                            try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {

                                String SQL = "DELETE FROM " + libraryName + " WHERE book_id = ?";
                                PreparedStatement statement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                                statement.setInt(1, bookRemoval.getBookID());
                                statement.executeUpdate();
                            }


                        }).build();
    }
}
