package service.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.auldfellas.AFQService;
import service.core.*;
import service.messages.*;

public class Actor extends AbstractActor {

      private LibraryService service = new BService();
      
      public static void main(String[] args) {
      
            // start the actor system and initlalize the Quoter actor
            ActorSystem system = ActorSystem.create();
            ActorRef ref = system.actorOf(Props.create(Recipt.class), "BorrowService");
            ref.tell(new Init(new BorrowService()), null);

            // send the message to the broker to register
            ActorSelection selection =
            system.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
            selection.tell("register", ref);      
      }

}