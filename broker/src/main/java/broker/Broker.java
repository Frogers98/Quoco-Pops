package broker;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import messages.catalogue.*;

import java.util.HashMap;

public class Broker extends AbstractActor {

    private static HashMap<String, ActorRef> actorRefs = new HashMap<>(); // This will store the ActorRefs for all services
    private ActorRef brokerRef;

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class,
                        msg -> {
                    // This can take in register_____ messages from each of the services and store it in a hash map
                            // with the key as the name of the service
                    System.out.println("Message received:" +  msg);
                            if (!msg.startsWith("register")) return;
                            // Print the actorRef to see if it's functioning
                            if (msg.equals("registerCatalogue")) {
                            System.out.println(getSender().toString());
                            // Store the actor ref for catalogue in the hash map
                            actorRefs.put("catalogue", getSender());
                        }
                        }).build();
    }


}

