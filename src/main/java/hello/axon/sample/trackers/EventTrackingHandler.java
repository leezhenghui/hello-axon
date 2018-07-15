package hello.axon.sample.trackers;

import hello.axon.sample.events.*;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("trackingpoint")
public class EventTrackingHandler {

    private static final Logger LOG = LoggerFactory.getLogger(EventTrackingHandler.class);

    @EventHandler
    public void on(CalculatorCreatedEvent cce) {
        LOG.info("EventType: {} ID: {} Owner: {} InitValue: {}", CalculatorCreatedEvent.class.getName(), cce.id, cce.owner, cce.initValue);
    }

    @EventHandler
    public void on(AddedEvent ae) {
        LOG.info("EventType: {} ID: {} Value: {}", AddedEvent.class.getName(), ae.id, ae.num);
        // Thread.dumpStack();
    }

    @EventHandler
    public void on(SubstractedEvent se) {
        LOG.info("EventType: {} ID: {} Value: {}", SubstractedEvent.class.getName(), se.id, se.num);
    }

    @EventHandler
    public void on(ResetEvent re) {
        LOG.info("EventType: {} ID: {}", ResetEvent.class.getName(), re.id);
    }

    @EventHandler
    public void on(CalculatorDestroyedEvent de) {
        LOG.info("EventType: {} ID: {}", CalculatorDestroyedEvent.class.getName(), de.id);
    }
}
