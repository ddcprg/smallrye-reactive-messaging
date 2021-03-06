package acme;

import io.smallrye.reactive.messaging.annotations.Emitter;
import io.smallrye.reactive.messaging.annotations.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class BeanUsingAnEmitter {

  @Inject @Stream("my-stream") Emitter<String> emitter;

  public void periodicallySendMessageToKafka() {
    AtomicInteger counter = new AtomicInteger();
    Executors.newSingleThreadScheduledExecutor()
      .scheduleAtFixedRate(() -> {
          emitter.send("Hello " + counter.getAndIncrement());
        },
        1, 1, TimeUnit.SECONDS);
  }

}
