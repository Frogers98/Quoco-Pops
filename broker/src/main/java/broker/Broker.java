package broker;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.typed.javadsl.AskPattern;
import akka.actor.Scheduler;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;
import messages.catalogue.*;
import messages.catalogue.CatalogueAddition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.Directives.complete;
import static akka.http.javadsl.server.Directives.onSuccess;
import static akka.pattern.Patterns.ask;

public class Broker extends AbstractActor {
    //    private Duration askTimeout;
//    private Scheduler scheduler;
    private static HashMap<String, ActorRef> actorRefs = new HashMap<>(); // This will store the ActorRefs for all services
    private ActorRef brokerRef;
    private static Duration timeout = Duration.ofSeconds(5);

//    public Broker(ActorSystem system, ActorRef brokerRef) {
//        this.brokerRef = brokerRef;
//        scheduler = system.scheduler();
//        askTimeout = system.settings().config().getDuration("my-app.routes.ask-timeout");
//    }
//
//    public Broker() {}



//    private CompletionStage<UserRegistry.Users> getBook() {
//        return AskPattern.ask(userRegistryActor, UserRegistry.GetUsers::new, askTimeout, scheduler);
//    }

    //    public Route catalogueRoutes() {
//        return pathPrefix("catalogue", () ->
//                concat(
//                        // Matches localhost:8080/catalogue/<book_id>
//                        path(PathMatchers.segment(), (String book_id) ->
//                                concat(
//                                        get(() ->
//                                                        //#retrieve-user-info
//                                                        rejectEmptyResponse(() ->
//                                                                onSuccess(getUser(name), performed ->
//                                                                        complete(StatusCodes.OK, performed.maybeUser, Jackson.marshaller())
//                                                                )
//                                                        )
//                                                //#retrieve-user-info
//                                        ),
//                                        delete(() ->
//                                                        //#users-delete-logic
//                                                        onSuccess(deleteUser(name), performed -> {
//                                                                    log.info("Delete result: {}", performed.description);
//                                                                    return complete(StatusCodes.OK, performed, Jackson.marshaller());
//                                                                }
//                                                        )
//                                                //#users-delete-logic
//                                        )
//                                )
//                        )
//                        //#users-get-post
//                )
//        );
    // }

    private String deleteBook() {
        // FIre and forget method that sends a CatalogueRemoval object to the catalogue service and returns a string when done
        CatalogueRemoval bookRemoval = new CatalogueRemoval(3, "tallaght_library");
        actorRefs.get("catalogue").tell(bookRemoval, getSelf());
        return "done";
    }

//    private CompletionStage<UserRegistry.GetUserResponse> getBook(String name) {
//        return AskPattern.ask(userRegistryActor, ref -> new UserRegistry.GetUser(name, ref), askTimeout, scheduler);
//    }

    public static Route userRoutes() {
        return pathPrefix("hello-world", () ->
                // Accessible at localhost:8080/hello-world
                concat(

                        pathEnd(() ->
                                get(() -> {
                                            //String complete = deleteBook();
//                                return complete("Test api call for broker");
//                                            CompletionStage<SearchResponse> future = ask(actorRefs.get("catalogue"), new SearchRequest(3, 1), timeout);
//                                            return completeOKWithFuture(future);

                                // Adds this mock book to the database and outputs a string to the browser
                                    // need to check database manually to verify it worked
                                actorRefs.get("catalogue").tell(new CatalogueAddition(3, "Python for Dummies", "John Smith", "tallaght_library", 10), null);
                                return complete("request to add to database sent");
//                                                onSuccess(getBook(),
//                                                        book -> complete(StatusCodes.OK, "success")
//                                                )
                                        }

                                )
                        )
                )
        );
    }

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

//    public static void main(String[] args) {
//        final Broker myServer = new Broker();
//        try {
//            myServer.startServer("localhost", 8080);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}

