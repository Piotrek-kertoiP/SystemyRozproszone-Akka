package Z3;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z3_SinkActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(Integer.class, i -> System.out.println("Got integer: " + i))
                .match(String.class, str -> System.out.println("Got message: " + str))
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}



