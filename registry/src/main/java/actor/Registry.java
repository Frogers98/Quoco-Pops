package actor;

import akka.actor.AbstractActor;
import messages.registry.CalculateFines;
import messages.registry.DeleteMember;
import messages.registry.RegisterMember;
import messages.registry.RetrieveMemberDetails;
import messages.registry.UpdatePassword;
import registry.RegistryService;

public class Registry extends AbstractActor {
    private RegistryService service;

    @Override
    public Receive createReceive() {
        return receiveBuilder().

        match(RegisterMember.class,
        msg -> {
            service.registerMember(msg.getMember());
        }).
        match(DeleteMember.class,
        msg -> {
            service.deleteMember(msg.getId());
        }).
        match(RetrieveMemberDetails.class,
        msg -> {
            service.retrieveMemberDetails(msg.getId());
        }).
        match(UpdatePassword.class,
        msg -> {
            service.updatePassword(msg.getId(), msg.getOldPassword(), msg.getNewPassword());
        }).
        match(CalculateFines.class,
        msg -> {
            service.calculateFinesOwed(msg.getId());

        }).build();
    }
    
}
