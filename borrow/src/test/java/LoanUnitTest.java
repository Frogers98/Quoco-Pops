import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import borrow.LoanService;
import messages.borrow.LoanBookRequest;
import messages.borrow.*;
import org.junit.*;

import java.time.Duration;

public class LoanUnitTest {
    static ActorSystem loanSystem;
    static ActorRef loanService;
    // private final static String dBURL = "jdbc:mysql://test.c2qef7oxk1tu.eu-west-1.rds.amazonaws.com:3306/loans";
    // private final static String dbUsername = "admin";
    // private final static String dbPassword = "Passw0rd1";


    @BeforeClass
    public static void setup() {
        loanSystem = ActorSystem.create();
        // Create an actor for this ActorSystem for this class
        loanService = loanSystem.actorOf(Props.create(LoanService.class), "testLoan");
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(loanSystem);
        loanSystem = null;
    }

   @Test
   public void testLoanAddition() {
       TestKit bookLoanActor = new TestKit(loanSystem);
       LoanBookRequest bookLoan = new LoanBookRequest(1, 1, 43, "a", "b", 1, "c");
       loanService.tell(bookLoan, bookLoanActor.getRef());

       bookLoanActor.awaitCond(bookLoanActor::msgAvailable);
       bookLoanActor.expectMsg(Duration.ZERO, "bookAdditionSuccess");
       return;
   }

//    @Test
//    public void testBookSearch() {
//        TestKit loanSearchActor = new TestKit(laonSystem);
//        RetrieveLoan loanSearch = new RetrieveLoan(3, 1);
//        laonService.tell(loanSearch, loanSearchActor.getRef());
//
//        loanSearchActor.awaitCond(loanSearchActor::msgAvailable);
//        loanSearchActor.expectMsgClass(Duration.ofSeconds(60), SearchResponse.class);
//
//        return;
//    }
//
//    @AfterClass
//    public static void teardown() {
//        TestKit.shutdownActorSystem(laonSystem);
//        laonSystem = null;
//    }
//
//   @Test
//   public void testLoanAddition() {
//       // Very basic test that sends a bookAddition message to the catalogue service and waits for a string response.
//       // I've temporarily made the catalogue service send a string message back to the sender if the db write is succesful
//       // but this should be made more robust
//       TestKit bookLoanActor = new TestKit(laonSystem);
//       //(loan_id, book_id, member_id, loan_date, return_date, library_ref)"
//       LoanBookRequest bookAddition = new LoanBookRequest(1,3,43,"", "", 0, "");
//       laonService.tell(bookAddition, bookLoanActor.getRef());
//
//       bookLoanActor.awaitCond(bookLoanActor::msgAvailable);
//       bookLoanActor.expectMsg(Duration.ZERO, "bookAdditionSuccess");
//       return;
//   }
//
////    @Test
////    public void testBookSearch() {
////        TestKit loanSearchActor = new TestKit(laonSystem);
////        RetrieveLoan loanSearch = new RetrieveLoan(3, 1);
////        laonService.tell(loanSearch, loanSearchActor.getRef());
////
////        loanSearchActor.awaitCond(loanSearchActor::msgAvailable);
////        loanSearchActor.expectMsgClass(Duration.ofSeconds(60), SearchResponse.class);
////
////        return;
////    }
}
