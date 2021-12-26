import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import borrow.LoanService;
import messages.borrow.LoanAddition;
import messages.borrow.*;
import org.junit.*;

import java.time.Duration;

public class LoanUnitTest {
    static ActorSystem laonSystem;
    static ActorRef laonService;
    private final static String dBURL = "jdbc:mysql://localhost:3306/ds_project";
    private final static String dbUsername = "borrow";
    private final static String dbPassword = "Passw0rd1";

    @BeforeClass
    public static void setup() {
        laonSystem = ActorSystem.create();
        // Create an actor for this ActorSystem for this class
        laonService = laonSystem.actorOf(Props.create(LoanService.class), "testLoan");
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(laonSystem);
        laonSystem = null;
    }

   @Test
   public void testLoanAddition() {
       // Very basic test that sends a bookAddition message to the catalogue service and waits for a string response.
       // I've temporarily made the catalogue service send a string message back to the sender if the db write is succesful
       // but this should be made more robust
       TestKit bookLoanActor = new TestKit(laonSystem);
       LoanAddition bookAddition = new LoanAddition(1,3,43,1,"", "");
       laonService.tell(bookAddition, bookLoanActor.getRef());

       bookLoanActor.awaitCond(bookLoanActor::msgAvailable);
       bookLoanActor.expectMsg(Duration.ZERO, "bookAdditionSuccess");
       return;
   }

//    @Test
//    public void testBookRemoval() {
//        TestKit bookRemovalActor = new TestKit(catalogueSystem);
//        CatalogueRemoval bookRemoval = new CatalogueRemoval(3, "tallaght_library");
//        catalogueService.tell(bookRemoval, bookRemovalActor.getRef());
//
//        bookRemovalActor.awaitCond(bookRemovalActor::msgAvailable);
//        bookRemovalActor.expectMsg(Duration.ofSeconds(60), "bookRemovalSuccess");
//
//        return;
//    }

    // @Test
    // public void testBookSearch() {
    //     TestKit bookSearchActor = new TestKit(catalogueSystem);
    //     SearchRequest bookSearch = new SearchRequest(3, 1);
    //     catalogueService.tell(bookSearch, bookSearchActor.getRef());

    //     bookSearchActor.awaitCond(bookSearchActor::msgAvailable);
    //     bookSearchActor.expectMsgClass(Duration.ofSeconds(60), SearchResponse.class);

    //     return;
    // }
}
