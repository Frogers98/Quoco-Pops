package broker;

import akka.http.javadsl.server.HttpApp;
import akka.http.javadsl.server.Route;

import akka.Done;
import akka.actor.ActorSystem;
import java.util.Optional;
import java.util.concurrent.*;

public class Broker extends HttpApp {

    @Override
    protected Route routes() {
        return path("hello-world", () ->
                get(() ->
                        complete("Test api call for broker")
                )
        );
    }

    public static void main(String[] args) {
        final Broker myServer = new Broker();
        try {
            myServer.startServer("localhost", 8080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

