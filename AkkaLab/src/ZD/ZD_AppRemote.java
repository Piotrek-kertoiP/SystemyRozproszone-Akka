package ZD;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ZD_AppRemote {

    public static void main(String[] args) throws Exception {

        // config
        File configFile = new File("remote_app2.conf");
        Config config = ConfigFactory.parseFile(configFile);

        // create actor system & actors
        final ActorSystem system = ActorSystem.create("remote_system", config);
        final ActorRef remote = system.actorOf(Props.create(ZD_RemoteActor.class), "remote");

        // interaction
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Type in \"q\" to quit");
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            }
            //remote.tell(line, null);
        }

        system.terminate();
    }
}