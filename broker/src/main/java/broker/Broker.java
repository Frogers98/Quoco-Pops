package broker;

import akka.actor.*;
import messages.catalogue.CatalogueAddition;

import java.util.ArrayList;
import java.util.HashMap;

public class Broker extends AbstractActor {
    private static ActorSystem brokerSystem;
    private static HashMap<String, ActorRef> actorRefs = new HashMap<>();
    private static ActorRef catalogueService;


    public static void main(String args[]) {
        brokerSystem = ActorSystem.create();
        ActorRef ref = brokerSystem.actorOf(Props.create(Broker.class), "broker");
//        ActorSelection selection =
//                brokerSystem.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
//        selection.tell("register", ref);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class,
                        msg -> {
                            // This is currently just an example block which gets called from the main method in
                            // CatalogueService, it then sends a CatalogueAddition request to the catalogue service
                            // which adds the example book below just to demonstrate db connectivity
                            if (!msg.equals("registerCatalogue")) return;
                            catalogueService  = getSender();
                            System.out.println("Look here, " + catalogueService.toString());
//                            actorRefs.put(ref.toString(), ref);

                            // Send a test request to catalogue to register a book
                            CatalogueAddition bookAddition = new CatalogueAddition(3,
                                    "Python for Dummies",
                                    "John Smith",
                                    "tallaght_library",
                                    10);


//                                catalogueService = actorRefs.get("catalogue");
                                catalogueService.tell(bookAddition, getSelf());

                        }).build();
        }
    }

