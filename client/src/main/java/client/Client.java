package client;
import akka.actor.*;
import messages.catalogue.*;

import java.sql.*;

public class Client extends AbstractActor {
    private static ActorRef clientRef;
    private static ActorSystem clientSystem;
    private static ActorRef brokerRef;


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
                                // Now that we have the broker ActorRef we can call our test method to try and send a message to it
                                testClientMessage();
                            }
                        }).build();
    }

    public void testClientMessage() {
        //Test message to add a book to the catalogue service
                CatalogueAddition bookAddition = new CatalogueAddition(4,
                "Akka for Dummies",
                "John Doe",
                "tallaght_library",
                5);
        brokerRef.tell(bookAddition, getSelf());
    }

}
