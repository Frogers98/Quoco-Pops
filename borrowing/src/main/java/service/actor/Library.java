package service.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.borrowing.BService;
import service.core.*;
import service.messages.*;

public class Library extends AbstractActor {

      private LibraryService service = new BService();
      
      public static void main(String[] args) {
      
            // start the actor system and initlalize the Library actor
            ActorSystem system = ActorSystem.create();
            ActorRef ref = system.actorOf(Props.create(Library.class), "BorrowService");
            ref.tell(new Init(new BService()), null);

            // send the message to the broker to register
            ActorSelection selection =
            system.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
            selection.tell("register", ref);      
      }

      public Receive createReceive() {
            return receiveBuilder()
            .match(Init.class,
                  msg -> {
                        service = msg.getService();
                        
                  })
                  .match(BorrowResponse.class,
              
                  msg -> {
                        Recipt recipt = service.generateRecipt(msg.getClientInfo());
                        
                        getSender().tell(new BorrowResponse(msg.getId(), recipt), getSelf());
              
                  }).build();
                  
            }
       
            

}