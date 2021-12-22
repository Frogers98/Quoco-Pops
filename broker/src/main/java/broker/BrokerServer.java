package broker;

import akka.NotUsed;
import akka.actor.Props;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.Behaviors;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.server.Route;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletionStage;

import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.actor.typed.javadsl.Adapter;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletionStage;

public class BrokerServer {
    // In the lightbend tutorial there is no static system as the ActorSystem and ActorRef were created in a different manner
    // unsure if this could lead to issues down the road but made more sense to me as startHttpServer and main are both
    // static methods? Feel free to change if another way makes more sense
    private static ActorSystem system;



    // #start-http-server
    static void startHttpServer(Route route) {
        CompletionStage<ServerBinding> futureBinding =
                Http.get(system).newServerAt("localhost", 8080).bind(route);

        futureBinding.whenComplete((binding, exception) -> {
            if (binding != null) {
                InetSocketAddress address = binding.localAddress();
                system.log().info("Server online at http://{}:{}/",
                        address.getHostString(),
                        address.getPort());
                System.out.println();
            } else {
                system.log().error("Failed to bind HTTP endpoint, terminating system", exception);
                system.terminate();
            }
        });
    }


    public static void main(String[] args) throws Exception {

        //Adapted from lightbend tutorial - create Actor system and actor for the broker and then start the server

        // Removed the Behaviour arrow function as that belongs to Akka-Typed
//        Behavior rootBehavior = Behaviors.setup(context -> {

            ActorSystem brokerSystem = ActorSystem.create();
            system = brokerSystem;
            ActorRef brokerRef = system.actorOf(Props.create(Broker.class), "broker");
//            Broker broker = new Broker(system, brokerRef);
            startHttpServer(Broker.userRoutes());

//            return Behaviors.empty();
//        });

        // boot up server using the route as defined below
//        ActorSystem.create(rootBehavior, "HelloAkkaHttpServer");
        //#server-bootstrapping
    }

}








