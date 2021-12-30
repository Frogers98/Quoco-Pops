package catalogue;

import akka.actor.*;
import messages.catalogue.*;

import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;

public class CatalogueService extends AbstractActor {
    static ActorSystem catalogueSystem;
    private final static String dBURL = "jdbc:mysql://localhost:3306/ds_project";
    private final static String dbUsername = "root";
    private final static String dbPassword = "Passw0rd1";
    private static ArrayList<String> libraryNames = new ArrayList<>();
    private static ActorRef catalogueActorRef;
    private static ActorRef brokerRef;


    public static void main(String[] args) {
        // Set up actor system, this method should be called initially before anything else in the class
        catalogueSystem = ActorSystem.create();
        // Create an actor for this ActorSystem for this class
        catalogueActorRef = catalogueSystem.actorOf(Props.create(CatalogueService.class), "catalogue");

        // Register this with the broker
        ActorSelection brokerSelection = catalogueSystem.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
        brokerSelection.tell("registerCatalogue", catalogueActorRef);
        //TEMPORARY - add tallaght library to libraryNames, library names should really be stored in a database or somewhere else
        libraryNames.add("tallaght_library");
        System.out.println("Catalogue started");

    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SearchRequest.class,
                        // Do a lookup in the database for the book by the book id and send a SearchResponse message
                        // back to the broker
                        // This should loop through all the library names in the system (currently represented in an
                        // ArrayList) and return a SearchResponse object for each library to the sender
                        // No current support for a search for one specific library, this needs to be added either here somehow
                        // or possibly with a different class for searching a specific library

                        //N.B. Current error with looping through libraries because of lack of error handling and using the ArrayList
                        // Hardcoded tallaght library as the only library to search for now and working
                        searchRequest -> {
                            // try with block to instantiate database stuff so it will close itself when finished
                            try (Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword)) {
                                // Search for the book in every library
                                //for (String libraryName : libraryNames) {
                                String libraryName = "tallaght_library";
                                    String SQL = "SELECT * FROM " + libraryName + " WHERE book_id =?";
                                    PreparedStatement statement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                                    statement.setInt(1, searchRequest.getBookId());
                                    ResultSet res = statement.executeQuery();
                                    // Create a SearchResponse object with the result and send it back to the broker
                                    while (res.next()) {
                                        SearchResponse response = new SearchResponse(
                                                res.getInt("book_id"),
                                                res.getString("book_title"),
                                                res.getString("book_author"),
                                                libraryName,
                                                res.getInt("available_copies"),
                                                res.getInt("total_copies"),
                                                searchRequest.getSearchId()
                                        );
                                        System.out.println("book found in " + libraryName + ". title: " + res.getString("book_title"));
                                        getSender().tell(response, getSelf());

                                    }
                                //END FOR LOOP}
                            } catch (SQLException e) {
                                e.printStackTrace();;
                            }


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

                                // Execute the sql query (returns the rows affected by the query
                                int rowsAffected = statement.executeUpdate();
                                // if at least one row was affected send a string back to the sender to indicate success
                                // this is only a temporary fix for the unit test as it waits for a string after sending a
                                // test bookAddition to this service
                                if (rowsAffected > 0) {
                                    getSender().tell("bookRemovalSuccess", getSelf());
                                }
                            }


                        })
                .match(String.class,
                        msg -> {
                    if (msg.equals("Tick")) {
                        // If the broker ActorRef hasn't been registered yet sleep for 2 seconds
//                        while (brokerRef == null) {
//                            Thread.sleep(2000);
//                        }
                        brokerRef.tell("testScheduler", getSelf());
                    }
                    else if (msg.equals("registerBroker")) {
                        brokerRef = getSender();
                        startScheduler();
                    }
                        }).build();
    }

//    class Ticker extends AbstractActor {
//        @Override
//        public Receive createReceive() {
//            return receiveBuilder()
//                    .matchEquals(
//                            "Tick",
//                            m -> {
//                                // Do someting
//                            })
//                    .build();
//        }
//    }

//    ActorRef tickActor = catalogueSystem.actorOf(Props.create(Ticker.class, this));

    // This will schedule to send the Tick-message
    // to the tickActor after 0ms repeating every 50ms
    public static void startScheduler() {
        Cancellable cancellable =
                catalogueSystem
                        .scheduler()
                        .schedule(
                                Duration.ofMillis(5000), Duration.ofMillis(3000), catalogueActorRef, "Tick", catalogueSystem.dispatcher(), null);
    }

    // This cancels further Ticks to be sent
//    cancellable.cancel();
    // #schedule-recurring
//    system.stop(tickActor);

}
