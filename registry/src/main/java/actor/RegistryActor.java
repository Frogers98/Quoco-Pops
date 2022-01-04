package actor;

import akka.actor.AbstractActor;
import core.Member;
import messages.Init;
import messages.borrow.CalculateFinesRequest;
import messages.borrow.CalculateFinesResponse;
import messages.registry.DeleteMemberRequest;
import messages.registry.RegisterMemberRequest;
import messages.registry.RetrieveMemberDetailsRequest;
import messages.registry.RetrieveMemberDetailsResponse;
import messages.registry.UpdatePasswordRequest;
import registry.RegistryService;

public class RegistryActor extends AbstractActor {
    private RegistryService service;

    @Override
    public Receive createReceive() {
        return receiveBuilder().

        match(Init.class,
        msg -> {
            service = msg.getRegistryService();
        }).
        match(RegisterMemberRequest.class,
        msg -> {
            service.registerMember(msg.getMember());
            getSender().tell("memberRegistrationSuccess", getSelf());
        }).
        match(DeleteMemberRequest.class,
        msg -> {
            service.deleteMember(msg.getId());
            getSender().tell("memberDeletionSuccess", getSelf());
        }).
        match(RetrieveMemberDetailsRequest.class,
        msg -> {
            Member member = service.retrieveMemberDetails(msg.getId());
            getSender().tell(new RetrieveMemberDetailsResponse(msg.getLibraryRef(), member), getSelf());
        }).
        match(UpdatePasswordRequest.class,
        msg -> {
            Boolean update = service.updatePassword(msg.getId(), msg.getOldPassword(), msg.getNewPassword());
            if (update) {
                getSender().tell("passwordUpdateSuccess", getSelf());
            }
        }).
        match(CalculateFinesRequest.class,
        msg -> {
            int finesOwed = service.calculateFinesOwed(msg.getMemberId());
            getSender().tell(new CalculateFinesResponse(msg.getLibraryRef(), msg.getMemberId(), finesOwed), getSelf());
        }).build();
    }
    
}
