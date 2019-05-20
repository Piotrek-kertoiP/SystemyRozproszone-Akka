package ZD;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ZD_FindWorker extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Request.class, request -> {
                    String title = request.title;
                    String result = find(title);
                    System.out.println(result);
                    Response response = new Response(result, request.type);
                    getSender().tell(response, getSelf());
                })
                .matchAny(o -> log.info("received unknown message: " + o.getClass()))
                .build();
    }

    private String find(String title) {
        //todo
        return "not yet implemented";
    }
}

