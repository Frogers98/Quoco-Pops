import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import registry.RegistryService;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import org.junit.*;

import java.time.Duration;

public class RegistryUnitTest {
    static ActorSystem registrySystem;
    static ActorRef registryService;
    private final static String dBURL = "jdbc:mysql://localhost:3306/ds_project";
    private final static String dbUsername = "root";
    private final static String dbPassword = "Passw0rd1";

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

    @Test
    public void test() {

    }
}
