package actor;

import akka.actor.AbstractActor;
import messages.registry.CalculateFines;
import messages.registry.DeleteMember;
import messages.registry.RegisterMember;
import messages.registry.RetrieveMemberDetails;
import messages.registry.UpdatePassword;
import registry.RegistryService;

public class Borrow extends AbstractActor {
    private BorrowService service;

    @Override
    public Receive createReceive() {
        return receiveBuilder().

        match(registerBorrow.class,
        msg -> {
            service.registerBorrow(msg.getMember());
        }).build();
    }
    
}
