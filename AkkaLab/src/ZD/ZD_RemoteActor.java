package ZD;

import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

public class ZD_RemoteActor extends AbstractActor {

    // for logging
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    // must be implemented -> creates initial behaviour
    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(Request.class, request -> {
                    if (request.type == RequestType.SUBSCRIBE)
                        context().child("subscriptionWorker").get().tell(request, getSelf()); // send task to child
                    if (request.type == RequestType.FIND)
                        context().child("findWorker").get().tell(request, getSelf());
                    if (request.type == RequestType.ORDER)
                        context().child("orderWorker").get().tell(request, getSelf());
                })
                .match(Response.class, response -> {
                    getContext().actorSelection("akka.tcp://local_system@127.0.0.1:2552/user/local").tell(response, getSelf());
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    // optional
    @Override
    public void preStart() throws Exception {
        context().actorOf(Props.create(ZD_SubscriptionWorker.class), "subscriptionWorker");
        context().actorOf(Props.create(ZD_OrderWorker.class), "orderWorker");
        context().actorOf(Props.create(ZD_FindWorker.class), "findWorker");
    }

    private static SupervisorStrategy strategy
            = new OneForOneStrategy(10, Duration.create("1 minute"), DeciderBuilder
            .match(ArithmeticException.class, x -> resume())
            .matchAny(o -> restart())
            .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

}
