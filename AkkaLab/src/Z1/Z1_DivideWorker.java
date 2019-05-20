package Z1;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z1_DivideWorker extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    private int operationCount = 0;

    @Override
    public Receive createReceive() {
            return receiveBuilder()
                    .match(String.class, s -> {
                        String result = divide(s);
                        operationCount += 1;
                        getSender().tell("result: " + result + " (operation count: " + this.operationCount + ")", getSelf());
                    })
                    .matchAny(o -> log.info("received unknown message"))
                    .build();
    }
    
    private String divide(String s){
        String[] split = s.split(" ");
        int a = Integer.parseInt(split[1]);
        int b = Integer.parseInt(split[2]);
        return (a / b) + "";
    }
}
