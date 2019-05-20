package ZD;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class ZD_LocalActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(Request.class, request -> getContext().actorSelection("akka.tcp://remote_system@127.0.0.1:3552/user/remote").tell(request, getSelf()))
                .match(Response.class, response -> System.out.println("Got result back: " + response.toString()))
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
