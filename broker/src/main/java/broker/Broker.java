package broker;

import java.util.HashMap;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import messages.Init;
import messages.borrow.AddBorrowingPrivileges;
import messages.borrow.CalculateFinesRequest;
import messages.borrow.CalculateFinesResponse;
import messages.borrow.LoanRequest;
import messages.borrow.RemoveBorrowingPrivileges;
import messages.borrow.RetrieveLoan;
import messages.catalogue.CatalogueAdditionRequest;
import messages.catalogue.CatalogueRemovalRequest;
import messages.catalogue.DecrementAvailabilityRequest;
import messages.catalogue.SearchRequest;
import messages.catalogue.SearchResponse;
import messages.registry.DeleteMemberRequest;
import messages.registry.RegisterMemberRequest;
import messages.registry.RetrieveMemberDetailsRequest;
import messages.registry.RetrieveMemberDetailsResponse;
import messages.registry.UpdatePasswordRequest;

public class Broker extends AbstractActor {

    // Store ActorRefs for all services i.e. catalogue, borrow and registry
    private static HashMap<String, ActorRef> actorRefs = new HashMap<>();

    // Store ActorRefs for all clients i.e. libraries in the system
    private static ActorRef brokerRef;

    private static HashMap<String, ActorRef> clientRefs = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Init.class,
                        msg -> {
                            System.out.println("Broker initialised");
                            brokerRef = getSender();
                        })

                .match(CalculateFinesRequest.class,
                        msg -> {
                            clientRefs.put(msg.getLibraryRef(), getSender());
                            actorRefs.get("registry").tell(msg, getSelf());
                        })

                .match(CalculateFinesResponse.class,
                        msg -> {
                            clientRefs.get(msg.getLibraryRef()).tell(msg, getSelf());
                        })

                .match(RetrieveMemberDetailsRequest.class,
                        msg -> {
                            clientRefs.put(msg.getLibraryRef(), getSender());
                            actorRefs.get("registry").tell(msg, getSelf());
                        })

                .match(RetrieveMemberDetailsResponse.class,
                        msg -> {
                            clientRefs.get(msg.getLibraryRef()).tell(msg, getSelf());
                        })

                .match(RegisterMemberRequest.class,
                        msg -> {
                            clientRefs.put(msg.getLibraryRef(), getSender());
                            actorRefs.get("registry").tell(msg, getSelf());
                            actorRefs.get("loan").tell(new AddBorrowingPrivileges(msg.getMember().getId()),
                                    getSelf());
                        })

                .match(DeleteMemberRequest.class,
                        msg -> {
                            clientRefs.put(msg.getLibraryRef(), getSender());
                            actorRefs.get("registry").tell(msg, getSelf());
                            actorRefs.get("loan").tell(new RemoveBorrowingPrivileges(msg.getId()), getSelf());
                        })

                .match(UpdatePasswordRequest.class,
                        msg -> {
                            clientRefs.put(msg.getLibraryRef(), getSender());
                            actorRefs.get("registry").tell(msg, getSelf());
                        })

                .match(CatalogueAdditionRequest.class,
                        msg -> {
                            clientRefs.put(msg.getLibraryRef(), getSender());
                            actorRefs.get("catalogue").tell(msg, getSelf());
                        })

                .match(CatalogueRemovalRequest.class,
                        msg -> {
                            clientRefs.put(msg.getLibraryRef(), getSender());
                            actorRefs.get("catalogue").tell(msg, getSelf());
                        })

                .match(SearchRequest.class,
                        msg -> {
                            clientRefs.put(msg.getLibraryRef(), getSender());
                            actorRefs.get("catalogue").tell(msg, getSelf());
                        })

                .match(SearchResponse.class,
                        msg -> {
                            clientRefs.get(msg.getLibraryRef()).tell(msg, getSelf());
                        })

                .match(LoanRequest.class,
                        msg -> {
                            clientRefs.put(msg.getLibraryRef(), getSender());
                            actorRefs.get("loan").tell(msg, getSelf());
                            actorRefs.get("catalogue").tell(
                                    new DecrementAvailabilityRequest(msg.getLibraryRef(), msg.getBookID()), getSelf());
                        })

                .match(RetrieveLoan.class,
                        msg -> {
                            clientRefs.put(msg.getLibraryRef(), getSender());
                            actorRefs.get("loan").tell(msg, getSelf());
                        })
                .match(String.class,
                        msg -> {
                            // This can take in register_____ messages from each of the services and store
                            // it in a hash map
                            // with the key as the name of the service
                            System.out.println("Message received:" + msg);
                            // if (!msg.startsWith("register"))
                            // return;
                            if (msg.equals("registerCatalogue")) {
                                System.out.println(getSender().toString());
                                // Store the actor ref for catalogue in the hash map
                                actorRefs.put("catalogue", getSender());
                            }
                            if (msg.equals("registerRegistry")) {
                                System.out.println(getSender().toString());
                                actorRefs.put("registry", getSender());
                            }
                            if (msg.equals("registerLoan")) {
                                System.out.println(getSender().toString());
                                actorRefs.put("loan", getSender());
                            }
                        })
                .build();
    }

}
