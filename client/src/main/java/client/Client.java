package client;

import akka.actor.*;
import core.Book;
import messages.catalogue.*;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client extends AbstractActor {
    private static ActorRef clientRef;
    private static ActorSystem clientSystem;
    private static ActorRef brokerRef;
    public static Scanner in = new Scanner(System.in);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class,
                        msg -> {
                            if (msg.equals("registerBroker")) {
                                brokerRef = getSender();
                                System.out.println("registered broker in client");
                                // Now that we have the broker ActorRef we can call our test method to try and send a message to it
                                // testClientMessage();
                            }
                        }).build();
    }

    public static void main(String args[]) {
        clientSystem = ActorSystem.create();
        clientRef = clientSystem.actorOf(Props.create(Client.class), "client");
        // Register this with the broker
        ActorSelection brokerSelection = clientSystem.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
        brokerSelection.tell("registerClient", clientRef);
        System.out.println("Client started");
        greetUser();

    }

    public static void greetUser() {
//        String serviceChoice = "0";
        System.out.println("Hello, welcome to the distributed library system. Please enter one of the below options" +
                "to use one of our services.");

        // I had to make this its own method instead of having it here so that I can call it repeatedly, when I
        // tried to run this method repeatedly I ended up with too many threads.
        String serviceChoice = getServiceChoice();
//        while ((!serviceChoice.equals("1")) && (!serviceChoice.equals("2")) && (!serviceChoice.equals("3"))) {
//            System.out.println("Hello, welcome to the distributed library system. Please enter one of the below options" +
//                    "to use one of our services." +
//                    "\n1----------Register Service" +
//                    "\n2----------Borrowing Service" +
//                    "\n3----------Catalogue Service");
//
////        try {
//            serviceChoice = in.nextLine();
////        } catch (InputMismatchException e) {
////            System.out.println("Error, invalid input. Input must be either '1', '2' or '3'.");
////            in.nextLine();
////            greetUser();
////        }
        while ((!serviceChoice.equals("1")) && (!serviceChoice.equals("2")) && (!serviceChoice.equals("3"))) {
                // Check if input was valid and call this method again if it wasn't
                System.out.println("Error, invalid input. Input must be either '1', '2' or '3'.");
//            in.nextLine();
                serviceChoice = getServiceChoice();
            }
//        }
//
        System.out.println("Valid input");
    }

    public static String getServiceChoice() {
            System.out.println("Please enter one of the below options" +
                    "to use one of our services." +
                    "\n1----------Register Service" +
                    "\n2----------Borrowing Service" +
                    "\n3----------Catalogue Service");
            String serviceChoice = in.nextLine();
        return serviceChoice;
    }


//    public void testClientMessage() {
//        //Test message to add a book to the catalogue service
//                Book book = new Book(4,
//                "Akka for Dummies",
//                "John Doe",
//                "tallaght_library",
//                5);
//                CatalogueAdditionRequest bookAddition = new CatalogueAdditionRequest("tallaght_library", book);
//        brokerRef.tell(bookAddition, getSelf());
//    }

}
