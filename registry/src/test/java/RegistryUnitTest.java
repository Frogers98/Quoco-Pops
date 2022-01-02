import akka.actor.ActorSystem;

import akka.testkit.javadsl.TestKit;
import core.Member;
import messages.Init;
import messages.registry.DeleteMemberRequest;
import messages.registry.RegisterMemberRequest;
import messages.registry.RetrieveMemberDetailsRequest;
import messages.registry.RetrieveMemberDetailsResponse;
import messages.registry.UpdatePasswordRequest;
import registry.RegistryService;
import akka.actor.ActorRef;
import akka.actor.Props;
import org.junit.*;

import actor.RegistryActor;

import java.time.Duration;

//public class RegistryUnitTest {
//    static ActorSystem registrySystem;
//    static ActorRef registryService;
//
//    @BeforeClass
//    public static void setup() {
//        registrySystem = ActorSystem.create();
//
//        registryService = registrySystem.actorOf(Props.create(RegistryActor.class), "testRegistry");
//
//        registryService.tell(new Init(new RegistryService()), null);
//    }
//
//    @AfterClass
//    public static void teardown() {
//        TestKit.shutdownActorSystem(registrySystem);
//        registrySystem = null;
//    }
//
//    @Test
//    public void testMemberRegistration() {
//        TestKit memberRegistrationActor = new TestKit(registrySystem);
//        Member member = new Member("Jane Slevin", Member.getFemaleChar(), 1994, "123", 1, Member.getTallaghtLib(), "12345678", "jane@email.com");
//        RegisterMemberRequest memberRegistration = new RegisterMemberRequest(member.getHomeLibrary(), member);
//
//        registryService.tell(memberRegistration, memberRegistrationActor.getRef());
//
//
//        memberRegistrationActor.awaitCond(memberRegistrationActor::msgAvailable);
//        memberRegistrationActor.expectMsg(Duration.ZERO, "memberRegistrationSuccess");
//        return;
//    }
//
//    @Test
//    public void testMemberDeletion() {
//        TestKit memberDeletionActor = new TestKit(registrySystem);
//        DeleteMemberRequest memberDeletion = new DeleteMemberRequest("tallaght_library", 7);
//
//        registryService.tell(memberDeletion, memberDeletionActor.getRef());
//
//
//        memberDeletionActor.awaitCond(memberDeletionActor::msgAvailable);
//        memberDeletionActor.expectMsg(Duration.ZERO, "memberDeletionSuccess");
//        return;
//    }
//
//    @Test
//    public void testRetrieveMemberDetails() {
//        TestKit memberRetrievalActor = new TestKit(registrySystem);
//        RetrieveMemberDetailsRequest memberRetrieval = new RetrieveMemberDetailsRequest("tallaght_library", 9);
//
//        registryService.tell(memberRetrieval, memberRetrievalActor.getRef());
//
//        memberRetrievalActor.awaitCond(memberRetrievalActor::msgAvailable);
//        memberRetrievalActor.expectMsgClass(Duration.ZERO, RetrieveMemberDetailsResponse.class);
//        return;
//    }
//
//    @Test
//    public void testPasswordUpdate() {
//        TestKit passwordUpdateActor = new TestKit(registrySystem);
//        UpdatePasswordRequest passwordUpdate = new UpdatePasswordRequest("tallaght_library", 9, "123", "456");
//
//        registryService.tell(passwordUpdate, passwordUpdateActor.getRef());
//
//        passwordUpdateActor.awaitCond(passwordUpdateActor::msgAvailable);
//        passwordUpdateActor.expectMsg(Duration.ZERO, "passwordUpdateSuccess");
//
//        return;
////    }
//
//}
