package Z2;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z2_RemoteActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(String.class, str -> getContext().actorSelection("akka.tcp://local_system@127.0.0.1:2552/user/local").tell(str, getSelf()))
                //.match(String.class, str -> getSender().tell(new EchoResult(str.toUpperCase()), self()))
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
