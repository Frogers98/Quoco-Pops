package broker;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.typed.javadsl.AskPattern;
import akka.actor.Scheduler;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;


import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.Directives.complete;

public class Broker extends AbstractActor {
    private Duration askTimeout;
    private Scheduler scheduler;
    private static ArrayList<ActorRef> actorRefs = new ArrayList<>();
    private ActorRef brokerRef;

//    public Broker(ActorSystem system, ActorRef brokerRef) {
//        this.brokerRef = brokerRef;
//        scheduler = system.scheduler();
//        askTimeout = system.settings().config().getDuration("my-app.routes.ask-timeout");
//    }
//
//    public Broker() {}

    public static ArrayList<ActorRef> getActorRefs() {
        return actorRefs;
    }


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
    public static Route userRoutes() {
        return pathPrefix("hello-world", () ->
                concat(

                        pathEnd(() ->
                                get(() ->
                                        complete("Test api call for broker")

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
                            if (!msg.equals("register")) return;
                            actorRefs.add(getSender());
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

