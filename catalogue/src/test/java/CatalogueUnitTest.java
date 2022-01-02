import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import catalogue.CatalogueService;
import core.Book;
import messages.catalogue.*;
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

//    @Test
//    public void testBookAddition() {
//        // Very basic test that sends a bookAddition message to the catalogue service and waits for a string response.
//        // I've temporarily made the catalogue service send a string message back to the sender if the db write is succesful
//        // but this should be made more robust
//        TestKit bookAdditionActor = new TestKit(catalogueSystem);
//        Book book = new Book(3,
//                "Python for Dummies",
//                "John Smith",
//                "tallaght_library",
//                10);
//
//        CatalogueAdditionRequest bookAddition = new CatalogueAdditionRequest(book, 5);
//        catalogueService.tell(bookAddition, bookAdditionActor.getRef());
//
//        bookAdditionActor.awaitCond(bookAdditionActor::msgAvailable);
//        bookAdditionActor.expectMsgClass(Duration.ofSeconds(60), CatalogueAdditionResponse.class);
//        return;
//    }

//    @Test
//    public void testBookRemoval() {
//        TestKit bookRemovalActor = new TestKit(catalogueSystem);
//        CatalogueRemovalRequest bookRemoval = new CatalogueRemovalRequest(3, "tallaght_library", 4);
//        catalogueService.tell(bookRemoval, bookRemovalActor.getRef());
//
//        bookRemovalActor.awaitCond(bookRemovalActor::msgAvailable);
//      bookRemovalActor.expectMsgClass(Duration.ofSeconds(60), CatalogueRemovalResponse.class);
//
//        return;
//    }

    @Test
    public void testBookSearch() {
        TestKit bookSearchActor = new TestKit(catalogueSystem);
        SearchRequest bookSearch = new SearchRequest("tallaght_library", 3, 1);
        catalogueService.tell(bookSearch, bookSearchActor.getRef());

        bookSearchActor.awaitCond(bookSearchActor::msgAvailable);
        bookSearchActor.expectMsgClass(Duration.ofSeconds(60), SearchResponse.class);

        return;
    }
}
