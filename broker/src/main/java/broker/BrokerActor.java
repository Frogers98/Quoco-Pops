//package broker;
//
//import akka.actor.AbstractActor;
//import akka.actor.ActorRef;
//import akka.actor.ActorSystem;
//
//import java.util.ArrayList;
//
//public class BrokerActor extends AbstractActor {
//    private static ActorSystem brokerSystem;
//    private static ArrayList<ActorRef> actorRefs = new ArrayList<>();
////    private static int SEED_ID = 1;
////    private static HashMap<Integer, ArrayList<Quotation>> cache = new HashMap<>();
////    private static HashMap<Integer, ClientInfo> clientsMap = new HashMap<>();
//    public static ArrayList<ActorRef> getActorRefs() {
//        return actorRefs;
//    }
//
//    @Override
//    public Receive createReceive() {
//        return receiveBuilder()
//                .match(String.class,
//                        msg -> {
//                            if (!msg.equals("register")) return;
//                            actorRefs.add(getSender());
//                        }).build();
//    }
//}
//
