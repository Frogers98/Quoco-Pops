import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import catalogue.CatalogueService;
import messages.catalogue.CatalogueAddition;
import org.junit.*;

import java.time.Duration;

public class CatalogueUnitTest {
    static ActorSystem catalogueSystem;
    static ActorRef catalogueService;
    private final static String dBURL = "jdbc:mysql://localhost:3306/ds_project";
    private final static String dbUsername = "root";
    private final static String dbPassword = "Passw0rd1";

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

    @Test
    public void testBookAddition() {
        // Very basic test that sends a bookAddition message to the catalogue service and waits for a string response.
        // I've temporarily made the catalogue service send a string message back to the sender if the db write is succesful
        // but this should be made more robust
        TestKit probe = new TestKit(catalogueSystem);
        CatalogueAddition bookAddition = new CatalogueAddition(3,
                "Python for Dummies",
                "John Smith",
                "tallaght_library",
                10);
        catalogueService.tell(bookAddition, probe.getRef());

        probe.awaitCond(probe::msgAvailable);
        probe.expectMsg(Duration.ZERO, "success");
    }
}
