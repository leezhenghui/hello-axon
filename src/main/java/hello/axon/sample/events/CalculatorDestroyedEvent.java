package hello.axon.sample.events;

public class CalculatorDestroyedEvent extends BaseEvent<String> {
    public final String EVENT_TYPE = "DESTROY";
    public CalculatorDestroyedEvent(String id) {
        super(id);
    }
}
