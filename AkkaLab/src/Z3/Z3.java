package Z3;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

public class Z3 {

    public static void main(String[] argv) throws Exception {

        final ActorSystem system = ActorSystem.create("stream_system");
        final Materializer materializer = ActorMaterializer.create(system);
        final ActorRef actor = system.actorOf(Props.create(Z3_SinkActor.class), "sink");

        final Source<Integer, NotUsed> source = Source.range(1, 10);    
        final Flow flow = Flow.of(Integer.class).scan(1, (i, acc) -> acc * i);

        source.via(flow).runWith(Sink.actorRef(actor, "Stream finished"), materializer);
    }
}
