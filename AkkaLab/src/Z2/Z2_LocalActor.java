package Z2;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z2_LocalActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, str -> getContext().actorSelection("akka.tcp://remote_system@127.0.0.1:3552/user/remote").tell(str, getSelf()))
                .match(EchoResult.class, result -> System.out.println("Got result back: " + result.result))
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
