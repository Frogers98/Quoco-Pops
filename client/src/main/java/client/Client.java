package client;

import akka.actor.*;
import core.Book;
import core.Member;
import messages.OperationStatusResponse;
import messages.borrow.*;
import messages.catalogue.*;
import messages.registry.*;

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
//                              // This is where all messages will be sent from the client to the broker
                                //Test message to add a book to the catalogue service
                                System.out.println("Beginning to send messages");
                                System.out.println("broker: " + brokerRef.toString());
//                                Book book = new Book(2,
//                                        "php for dummies",
//                                        "john doe",
//                                        "tall_lib",
//                                        5);
//                                CatalogueAdditionRequest bookAddition = new CatalogueAdditionRequest(book, 1);
//                                brokerRef.tell(bookAddition, getSelf());

                                // Test message to delete a book from the catalogue service
                                // CatalogueRemovalRequest bookRemoval = new CatalogueRemovalRequest(3, "tall_lib", 2);
                                // brokerRef.tell(bookRemoval, getSelf());

                                // Test message to search for a book using the catalogue service
                                SearchRequest searchRequest = new SearchRequest("phib_lib", 2, 123);
                                brokerRef.tell(searchRequest, getSelf());

                                // Check for availability of book (there is a copy of book 3 in tall_lib but not dl_lib in the database,
                                // so a AvailablaeLocallyResponse should be generated for the first request and a AvailableRemotelyResponse for
                                // the second.
                                CheckAvailabilityRequest availabilityRequest1 = new CheckAvailabilityRequest("tall_lib", 3, 3);
                                brokerRef.tell(availabilityRequest1, getSelf());
                                CheckAvailabilityRequest availabilityRequest2 = new CheckAvailabilityRequest("dl_lib", 3, 4);
                                brokerRef.tell(availabilityRequest2, getSelf());

                                // Test message to register a user
                                // Member registerMember = new Member(
                                //         "Grandpa Simpson",
                                //         'M',
                                //         1944,
                                //         "SoITiedAnOnionToMyBeltWhichWasTheStyleAtTheTime",
                                //         "phib_lib",
                                //         "67891234",
                                //         "grandpa@email.com");
                                // RegisterMemberRequest memberRequest = new RegisterMemberRequest(registerMember.getHomeLibrary(), registerMember);
                                // brokerRef.tell(memberRequest, getSelf());

                                // Test message to unregister a user
                                // DeleteMemberRequest memberDeletion = new DeleteMemberRequest("phib_lib", 5);
                                // brokerRef.tell(memberDeletion, getSelf());

                                // Test message to retrieve member details
                                RetrieveMemberDetailsRequest memberDetailsRequest = new RetrieveMemberDetailsRequest("tall_lib", 1);
                                brokerRef.tell(memberDetailsRequest, getSelf());

                                // Test message to update password
                                // UpdatePasswordRequest passwordRequest = new UpdatePasswordRequest("tall_lib", 1, "123", "LisaNeedsBraces");
                                // brokerRef.tell(passwordRequest, getSelf());

//                                 Test message to loan a book (Operation status request should be returned as this represents
//                                 a user scanning a physical book in person at a machine in the library)
                                LoanBookRequest loan = new LoanBookRequest(77, 1, " dl_lib");
                                brokerRef.tell(loan, getSelf());

//                                 Test message to retrieve info about a loan
                                RetrieveLoan retrieveLoanInfo = new RetrieveLoan("tall_lib", 1, 45);
                                brokerRef.tell(retrieveLoanInfo, getSelf());

                                // Test message to calculate fines on a loan
                                CalculateFinesRequest finesRequest = new CalculateFinesRequest("dl_lib", 21);
                                brokerRef.tell(finesRequest, getSelf());

                                // Test message to return a book (an OperationStatus message should be returned)
                                ReturnBookRequest returnBookRequest = new ReturnBookRequest(3, "phib_lib", 25, 64);
                                brokerRef.tell(returnBookRequest, getSelf());

                                System.out.println("Finished sending messages");
                            }
                        })
                .match(SearchResponse.class,
                        searchResponse -> {
                            Book returnedBook = searchResponse.getBook();
                            System.out.println("\n**********Search response for user: " + searchResponse.getUserId() +
                                    "\nTitle: " + returnedBook.getBookTitle() +
                                    "\nAuthor: " + returnedBook.getBookAuthor() +
                                    "\nBook ID code: " + returnedBook.getBookID() +
                                    "\n**********");
                        })
                .match(CatalogueAdditionResponse.class,
                        // Ideally we would have error handling for if a book addition is unsuccessful
                        additionResponse -> {
                            System.out.println("\n**********Book addition response for user: " + additionResponse.getUserId());
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
                            System.out.println("\n**********Book removal response for user: " + removalResponse.getUserId());
                            if (removalResponse.isSuccessfulAction()) {
                                System.out.println("\nBook removal was successful");
                            } else {
                                System.out.println("\nBook removal was unsuccessful");
                            }
                            System.out.println("*********");
                        })
                .match(AvailableLocallyResponse.class,
                        availLocalResponse -> {
                            System.out.println("\n*********\nAvailability response for user: " + availLocalResponse.getUserId() + ", your request is available locally at " + availLocalResponse.getLibraryRef() +
                                    "\nThere are currently " + availLocalResponse.getCopiesAvailable() + "copies of book id " + availLocalResponse.getBookId() + "available.\n*********");
                        })
                .match(AvailableRemotelyResponse.class,
                        availableRemoteResponse -> {
                            System.out.println("\n**********\nAvailability response for user: " + availableRemoteResponse.getUserId() +
                            ", your request is not available locally at " + availableRemoteResponse.getLibraryRef() +
                                    "\nbut can be found in the following locations at these distances:\n");
                            for (Map.Entry<String, Integer> entry : availableRemoteResponse.getWhereAvailable().entrySet()) {
                                String library = entry.getKey();
                                int distance = entry.getValue();
                                System.out.println(library + "-----" + distance);
                            }
                            System.out.println("*********");
                        })
                .match(OperationStatusResponse.class,
                        operationStatusResponse ->  {
                            System.out.println("\n**********\nOperation Status response for user: " + operationStatusResponse.getMemberId() +
                                    operationStatusResponse.getMessage() + "\n*********");
                        })
                .match(RetrieveMemberDetailsResponse.class,
                        memberDetailsResponse ->  {
                    Member member = memberDetailsResponse.getMember();
                            System.out.println("\n**********\nMember details response for user: " + member.getId() +
                                    "\nName: " + member.getName() +
                                    "\nGender: " + member.getGender() +
                                    "\nYear of Birth: " + member.getYearOfBirth() +
                                    "\nPassword: " + member.getPassword() +
                                    "\nHome Library: " + member.getHomeLibrary() +
                                    "\nPhone Number: " + member.getPhoneNumber() +
                                    "\nEmail: " + member.getEmail() + "\n**********");
                        })
                .match(LoanSearchResponse.class,
                        loanSearchResponse ->  {
                            System.out.println("\n**********\nLoan Search Response for user: " + loanSearchResponse.getUserID()+
                                    "\nLoan ID: " + loanSearchResponse.getLoanID() +
                                    "\nBook ID" + loanSearchResponse.getBookID() +
                                    "\nLoan Date: " + loanSearchResponse.getLoanDate() +
                                    "\nReturn Date: " + loanSearchResponse.getReturnDate() +
                                    "\n*********");
                        })
                .match(CalculateFinesResponse.class,
                        calculateFinesResponse ->  {
                            System.out.println("\n**********\nLoan Search Response for user: " + calculateFinesResponse.getId()+
                                    "\nTotal amount owed across all loans is: " + calculateFinesResponse.getTotal() +
                                    "\n*********");
                        })

                .build();
    }


}
