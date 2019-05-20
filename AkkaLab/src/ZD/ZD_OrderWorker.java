package ZD;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ZD_OrderWorker extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);



    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Request.class, request -> {
                    String title = request.title;
                    String result = order(title);
                    Response response = new Response(result, request.type);
                    System.out.println(response);
                    getSender().tell(response, getSelf());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    private String order(String title) {
        //todo
        return "not yet implemented";
    }

}


