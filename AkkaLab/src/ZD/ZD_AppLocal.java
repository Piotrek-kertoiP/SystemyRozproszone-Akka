package ZD;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ZD_AppLocal {

    public static void main(String[] args) throws Exception {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        // config
        File configFile = new File("remote_app.conf");
        Config config = ConfigFactory.parseFile(configFile);


        // create actor system & actors
        final ActorSystem system = ActorSystem.create("local_system", config);
        final ActorRef actor = system.actorOf(Props.create(ZD_LocalActor.class), "local");

        RequestType requestType = null;
        Request request;
        String title;

        while(true){
            System.out.println("\"S\": <title> - subscribe for a book; \"F\": <title> - find a book; \"O\": <title> - place an order for a book; \"q\" - quit");
            String input = bufferedReader.readLine();
            input = input.toLowerCase();
            //System.out.println("input = \"" + input + "\"");
            if(input.equals("q")){
                //break;
                //System.exit(0);
                system.terminate();
            }
            else{
                String[] command = input.split(":");
                if(command.length != 2){
                    System.out.println("Wrong command, try again - make sure the tittle doesn't have \":\" signs");
                    continue;
                }

                if(command[0].equals("s") || command[0].equals("f") || command[0].equals("o") ) {
                    if(command[0].equals("s")) requestType = RequestType.SUBSCRIBE;
                    if(command[0].equals("f")) requestType = RequestType.FIND;
                    if(command[0].equals("o")) requestType = RequestType.ORDER;

                    title = command[1];
                    request = new Request(requestType, title);
                    actor.tell(request, null);
                } else {
                    System.out.println("Wrong command, try again");
                }
            }
        }
    }
}
