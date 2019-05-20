package ZD;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import java.util.LinkedList;

import static java.lang.Thread.sleep;

public class ZD_SubscriptionWorker extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Request.class, request -> {
                    String title = request.title;

                    LinkedList<String> bookLinesList = getBookFromDataBase(title);
                    for(String line : bookLinesList){                               //todo: przerobić na streamowanie
                        System.out.println(line);
                        sleep(1000);
                        Response response = new Response(line, request.type);
                        getSender().tell(response, getSelf());
                    }
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    private LinkedList<String> getBookFromDataBase(String title) {
        //todo
        LinkedList<String> bookContent = new LinkedList<>();
        for(int i = 1; i <= 1000; i++){
            bookContent.add("Line no. " + i);
        }
        return bookContent;
    }

}



