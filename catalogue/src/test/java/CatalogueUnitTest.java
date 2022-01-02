import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import catalogue.CatalogueService;
import messages.catalogue.AvailableLocallyResponse;
import messages.catalogue.AvailableRemotelyResponse;
import messages.catalogue.CatalogueAdditionRequest;
import messages.catalogue.CatalogueRemovalRequest;
import messages.catalogue.CheckAvailabilityRequest;
import messages.catalogue.DecrementAvailabilityRequest;
import messages.catalogue.IncrementAvailabilityRequest;
import messages.catalogue.SearchRequest;
import messages.catalogue.SearchResponse;
import org.junit.*;

import java.time.Duration;

public class CatalogueUnitTest {
    static ActorSystem catalogueSystem;
    static ActorRef catalogueService;

    @BeforeClass
    public static void setup() {
        catalogueSystem = ActorSystem.create();
        // Create an actor for this ActorSystem for this class
        catalogueService = catalogueSystem.actorOf(Props.create(CatalogueService.class), "testCatalogue");
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(catalogueSystem);
        catalogueSystem = null;
    }

    // @Test
    // public void testBookAddition() {
    // // Very basic test that sends a bookAddition message to the catalogue service
    // and waits for a string response.
    // // I've temporarily made the catalogue service send a string message back to
    // the sender if the db write is succesful
    // // but this should be made more robust
    // TestKit bookAdditionActor = new TestKit(catalogueSystem);
    // CatalogueAddition bookAddition = new CatalogueAddition(3,
    // "Python for Dummies",
    // "John Smith",
    // "tallaght_library",
    // 10);
    // catalogueService.tell(bookAddition, bookAdditionActor.getRef());
    //
    // bookAdditionActor.awaitCond(bookAdditionActor::msgAvailable);
    // bookAdditionActor.expectMsg(Duration.ZERO, "bookAdditionSuccess");
    // return;
    // }

    // @Test
    // public void testBookRemoval() {
    // TestKit bookRemovalActor = new TestKit(catalogueSystem);
    // CatalogueRemoval bookRemoval = new CatalogueRemoval(3, "tallaght_library");
    // catalogueService.tell(bookRemoval, bookRemovalActor.getRef());
    //
    // bookRemovalActor.awaitCond(bookRemovalActor::msgAvailable);
    // bookRemovalActor.expectMsg(Duration.ofSeconds(60), "bookRemovalSuccess");
    //
    // return;
    // }

    // @Test
    // public void testBookSearch() {
    // TestKit bookSearchActor = new TestKit(catalogueSystem);
    // SearchRequest bookSearch = new SearchRequest(3, 1);
    // catalogueService.tell(bookSearch, bookSearchActor.getRef());
    //
    // bookSearchActor.awaitCond(bookSearchActor::msgAvailable);
    // bookSearchActor.expectMsgClass(Duration.ofSeconds(60), SearchResponse.class);
    //
    // return;
    // }

    // @Test
    // public void testAvailableLocally() {
    //     TestKit availableLocallyActor = new TestKit(catalogueSystem);
    //     CheckAvailabilityRequest req = new CheckAvailabilityRequest("dl_lib", 1);

    //     catalogueService.tell(req, availableLocallyActor.getRef());

    //     availableLocallyActor.awaitCond(availableLocallyActor::msgAvailable);
    //     availableLocallyActor.expectMsgClass(Duration.ZERO, AvailableLocallyResponse.class);
    //     return;
    // }

    // @Test
    // public void testAvailableRemotely() {
    //     TestKit availableRemotelyActor = new TestKit(catalogueSystem);
    //     CheckAvailabilityRequest req = new CheckAvailabilityRequest("tall_lib", 1);

    //     catalogueService.tell(req, availableRemotelyActor.getRef());

    //     availableRemotelyActor.awaitCond(availableRemotelyActor::msgAvailable);
    //     availableRemotelyActor.expectMsgClass(Duration.ZERO, AvailableRemotelyResponse.class);
    //     return;
    // }

    @Test
    public void testIncrementAvailability() {
        TestKit incrementAvailabilityActor = new TestKit(catalogueSystem);
        IncrementAvailabilityRequest req = new IncrementAvailabilityRequest("tall_lib", 1);

        catalogueService.tell(req, incrementAvailabilityActor.getRef());

        incrementAvailabilityActor.awaitCond(incrementAvailabilityActor::msgAvailable);
        incrementAvailabilityActor.expectMsg(Duration.ZERO, 1);
        return;
    }

    // @Test
    // public void testDecrementAvailability() {
    //     TestKit decrementAvailabilityActor = new TestKit(catalogueSystem);
    //     DecrementAvailabilityRequest req = new DecrementAvailabilityRequest("talll_lib", 1);

    //     catalogueService.tell(req, decrementAvailabilityActor.getRef());

    //     decrementAvailabilityActor.awaitCond(decrementAvailabilityActor::msgAvailable);
    //     decrementAvailabilityActor.expectMsg(Duration.ZERO, 1);
    //     return;
    // }


}
