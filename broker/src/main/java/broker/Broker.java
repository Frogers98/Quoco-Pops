package broker;

import akka.actor.*;
import messages.catalogue.*;

import java.util.HashMap;

public class Broker extends AbstractActor {

    private static HashMap<String, ActorRef> actorRefs = new HashMap<>(); // This will store the ActorRefs for all services
    private static ActorRef brokerRef;
    private static ActorSystem brokerSystem;

    public static void main(String[] args) {
        brokerSystem = ActorSystem.create();
        ActorRef brokerRef = brokerSystem.actorOf(Props.create(Broker.class), "broker");
        actorRefs.put("broker", brokerRef);
        System.out.println("Broker started");
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class,
                        msg -> {
                            // This can take in register_____ messages from each of the services and store it in a hash map
                            // with the key as the name of the service
                            // Test block for receiving scheduled message
                            if (msg.equals("testScheduler")) {
                                System.out.println(msg);
                            }
                            else {
                                if (!msg.startsWith("register")) return;

                                // Store the actor ref for whichever service registered in the hash map
                                if (msg.equals("registerCatalogue")) {
                                    actorRefs.put("catalogue", getSender());
                                } else if (msg.equals("registerRegistry")) {
                                    actorRefs.put("register", getSender());
                                } else if (msg.equals("registerBorrowing")) {
                                    actorRefs.put("borrowing", getSender());
                                }
                            }
                            // Send a register message back to whichever service just registered with the broker so that
                            // it has a copy of the brokers ActorRef
                            getSender().tell("registerBroker", getSelf());

                        }).build();
    }


}

