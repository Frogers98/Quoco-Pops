import akka.actor.ActorSystem;

import akka.testkit.javadsl.TestKit;
import core.Member;
import messages.OperationStatusResponse;
import messages.registry.DeleteMemberRequest;
import messages.registry.RegisterMemberRequest;
import messages.registry.RetrieveMemberDetailsRequest;
import messages.registry.RetrieveMemberDetailsResponse;
import messages.registry.UpdatePasswordRequest;
import registry.RegistryService;
import akka.actor.ActorRef;
import akka.actor.Props;
import org.junit.*;

import java.time.Duration;

public class RegistryUnitTest {
    static ActorSystem registrySystem;
    static ActorRef registryService;

    @BeforeClass
    public static void setup() {
        registrySystem = ActorSystem.create();

        registryService = registrySystem.actorOf(Props.create(RegistryService.class), "testRegistry");
        
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(registrySystem);
        registrySystem = null;
    }

    // @Test
    // public void testMemberRegistration() {
    //     TestKit memberRegistrationActor = new TestKit(registrySystem);
    //     Member member = new Member("Jane Slevin", Member.getFemaleChar(), 1994, "123", 100, "tall_lib", "12345678", "jane@email.com");
    //     RegisterMemberRequest memberRegistration = new RegisterMemberRequest(member.getHomeLibrary(), member);

    //     registryService.tell(memberRegistration, memberRegistrationActor.getRef());


    //     memberRegistrationActor.awaitCond(memberRegistrationActor::msgAvailable);
    //     memberRegistrationActor.expectMsgClass(Duration.ZERO, OperationStatusResponse.class);
    //     return;
    // }

    // @Test
    // public void testMemberDeletion() {
    //     TestKit memberDeletionActor = new TestKit(registrySystem);
    //     DeleteMemberRequest memberDeletion = new DeleteMemberRequest("tall_lib", 100);

    //     registryService.tell(memberDeletion, memberDeletionActor.getRef());


    //     memberDeletionActor.awaitCond(memberDeletionActor::msgAvailable);
    //     memberDeletionActor.expectMsgClass(Duration.ZERO, OperationStatusResponse.class);
    //     return;
    // }

    // @Test
    // public void testRetrieveMemberDetails() {
    //     TestKit memberRetrievalActor = new TestKit(registrySystem);
    //     RetrieveMemberDetailsRequest memberRetrieval = new RetrieveMemberDetailsRequest("tall_lib", 1);

    //     registryService.tell(memberRetrieval, memberRetrievalActor.getRef());

    //     memberRetrievalActor.awaitCond(memberRetrievalActor::msgAvailable);
    //     memberRetrievalActor.expectMsgClass(Duration.ZERO, RetrieveMemberDetailsResponse.class);
    //     return;
    // }

    // @Test 
    // public void testPasswordUpdate() {
    //     TestKit passwordUpdateActor = new TestKit(registrySystem);
    //     UpdatePasswordRequest passwordUpdate = new UpdatePasswordRequest("tall_lib", 1, "123", "456");

    //     registryService.tell(passwordUpdate, passwordUpdateActor.getRef());

    //     passwordUpdateActor.awaitCond(passwordUpdateActor::msgAvailable);
    //     passwordUpdateActor.expectMsgClass(Duration.ZERO, OperationStatusResponse.class);

    //     return;
    // }

}
