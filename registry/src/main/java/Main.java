import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import messages.Init;
import registry.RegistryService;
import java.sql.*;


public class Main {
    public static void main(String[] args) {
        final String dBURL = "jdbc:mysql://localhost:3306/ds_project";
        final String dbUsername = "root";
        final String dbPassword = "Passw0rd1";
        ActorSystem system = ActorSystem.create();

        ActorRef ref = system.actorOf(Props.create(RegistryService.class), "registry");
        ref.tell(new Init(new RegistryService()), null);

        ActorSelection selection = system.actorSelection("akka.tcp://default@127.0.0.1:2551/user/broker");
        selection.tell("registerRegistry", ref);

        // Open a connection
        try(Connection conn = DriverManager.getConnection(dBURL, dbUsername, dbPassword);
        Statement stmt = conn.createStatement();
        ) {		      
            String sql = "CREATE TABLE IF NOT EXISTS REGISTRATION " +
                    "(id INTEGER not NULL, " +
                    " name VARCHAR(255) NOT NULL, " + 
                    " gender VARCHAR(255), " + 
                    " year_of_birth INTEGER, " + 
                    " password VARCHAR(225) NOT NULL, " + 
                    " home_library VARCHAR(225), " + 
                    " email VARCHAR(225), " + 
                    " phone_no VARCHAR(225), " + 
                    " PRIMARY KEY ( id ))"; 
            stmt.executeUpdate(sql);
            System.out.println("Created REGISTRATION in given database...");   	  
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
}