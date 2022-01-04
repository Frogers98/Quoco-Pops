package client;

import akka.actor.*;
import core.Book;
import messages.catalogue.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Client extends AbstractActor {
    private static ActorRef clientRef;
    private static ActorSystem clientSystem;
    private static ActorRef brokerRef;

    public Client() {}

    public static void main(String args[]) {
        clientSystem = ActorSystem.create();
        clientRef = clientSystem.actorOf(Props.create(Client.class), "client");
        // Register this with the broker
        ActorSelection brokerSelection = clientSystem.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
        brokerSelection.tell("registerClient", clientRef);
        System.out.println("Client started");



    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class,
                        msg -> {
                            if (msg.equals("registerBroker")) {
                                brokerRef = getSender();
                                System.out.println("registered broker in client");
                                clientRef.tell("startSystem", null);
                                // Now that we have the broker ActorRef we can call our test method to try and send a message to it
//                                testClientMessage();
                            }
                            else if (msg.equals("startSystem")) {
                                //Test message to add a book to the catalogue service
                                System.out.println("Beginning to send messages");
                                System.out.println("broker: " + brokerRef.toString());
                                Book book = new Book(2,
                                        "php for dummies",
                                        "john doe",
                                        "tall_lib",
                                        5);
                                CatalogueAdditionRequest bookAddition = new CatalogueAdditionRequest(book, 1);
                                brokerRef.tell(bookAddition, getSelf());

                                // Test message to delete a book from the catalogue service
                                CatalogueRemovalRequest bookRemoval = new CatalogueRemovalRequest(3, "tall_lib", 2);
                                brokerRef.tell(bookRemoval, getSelf());

                                // Test message to search for a book using the catalogue service
                                SearchRequest searchRequest = new SearchRequest("phib_lib", 2, 123);
                                brokerRef.tell(searchRequest, getSelf());

                                // Check for availability of book (there is a copy of book 3 in tall_lib but not dl_lib in the database,
                                // so a AvailablaeLocallyResponse should be generated for the first request and a AvailableRemotelyResponse for
                                // the second.
                                CheckAvailabilityRequest availabilityRequest1 = new CheckAvailabilityRequest("tall_lib", 3, 3);
                                brokerRef.tell(availabilityRequest1, getSelf());
//        CheckAvailabilityRequest availabilityRequest2 = new CheckAvailabilityRequest("dl_lib", 3, 4);
//        brokerRef.tell(availabilityRequest2, getSelf());

                                System.out.println("Finished sending messages");
                            }
                        })
                .match(SearchResponse.class,
                        searchResponse -> {
                            Book returnedBook = searchResponse.getBook();
                            System.out.println("Search response for user: " + searchResponse.getUserId() + "\n**********" +
                                    "\nTitle: " + returnedBook.getBookTitle() +
                                    "\nAuthor: " + returnedBook.getBookAuthor() +
                                    "\nBook ID code: " + returnedBook.getBookID() +
                                    "\n**********");
                        })
                .match(CatalogueAdditionResponse.class,
                        // Ideally we would have error handling for if a book addition is unsuccessful
                        additionResponse -> {
                            System.out.println("Book addition response for user: " + additionResponse.getUserId() + "\n**********");
                            if (additionResponse.isSuccessfulAction()) {
                                System.out.println("\nBook addition was successful");
                            } else {
                                System.out.println("\nBook addition was unsuccessful");
                            }
                            System.out.println("*********");
                        })
                // Ideally we would have error handling for if a book addition is unsuccessful
                .match(CatalogueRemovalResponse.class,
                        removalResponse -> {
                            System.out.println("Book removal response for user: " + removalResponse.getUserId() + "\n**********");
                            if (removalResponse.isSuccessfulAction()) {
                                System.out.println("\nBook removal was successful");
                            } else {
                                System.out.println("\nBook removal was unsuccessful");
                            }
                            System.out.println("*********");
                        })
                .match(AvailableLocallyResponse.class,
                        availLocalResponse -> {
                            System.out.println("*********\nAvailability response for user: " + availLocalResponse.getUserId() + ", your request is available locally at " + availLocalResponse.getLibraryRef() +
                                    "\nThere are currently " + availLocalResponse.getCopiesAvailable() + "copies of book id " + availLocalResponse.getBookId() + "available.\n*********");
                        })
                .match(AvailableRemotelyResponse.class,
                        availableRemoteResponse -> {
                            System.out.println("**********\nAvailability response for user: " + availableRemoteResponse.getUserId() +
                            ", your request is not available locally at " + availableRemoteResponse.getLibraryRef() +
                                    "\nbut can be found in the following locations at these distances:\n");
                            for (Map.Entry<String, Integer> entry : availableRemoteResponse.getWhereAvailable().entrySet()) {
                                String library = entry.getKey();
                                int distance = entry.getValue();
                                System.out.println(library + "-----" + distance);
                            }
                            System.out.println("*********");
                        }).build();
    }

//    public void testClientMessage() {
//        //Test message to add a book to the catalogue service
//        System.out.println("Beginning to send messages");
//        Book book = new Book(2,
//                "php for dummies",
//                "john doe",
//                "tall_lib",
//                5);
//        CatalogueAdditionRequest bookAddition = new CatalogueAdditionRequest(book, 1);
//        brokerRef.tell(bookAddition, getSelf());
//
//        // Test message to delete a book from the catalogue service
//        CatalogueRemovalRequest bookRemoval = new CatalogueRemovalRequest(3, "tall_lib", 2);
//        brokerRef.tell(bookRemoval, getSelf());
//
//        // Test message to search for a book using the catalogue service
//        SearchRequest searchRequest = new SearchRequest("phib_lib", 2, 123);
//        brokerRef.tell(searchRequest, getSelf());
//
//        // Check for availability of book (there is a copy of book 3 in tall_lib but not dl_lib in the database,
//        // so a AvailablaeLocallyResponse should be generated for the first request and a AvailableRemotelyResponse for
//        // the second.
//        CheckAvailabilityRequest availabilityRequest1 = new CheckAvailabilityRequest("tall_lib", 3, 3);
//        brokerRef.tell(availabilityRequest1, getSelf());
////        CheckAvailabilityRequest availabilityRequest2 = new CheckAvailabilityRequest("dl_lib", 3, 4);
////        brokerRef.tell(availabilityRequest2, getSelf());
//
//
//    }

}
