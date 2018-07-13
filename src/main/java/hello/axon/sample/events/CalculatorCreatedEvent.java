package hello.axon.sample.events;

public class CalculatorCreatedEvent extends BaseEvent<String> {
    public final String EVENT_TYPE = "CREATE";
    public final double initValue;
    public final String owner;

    public CalculatorCreatedEvent(String id, String owner, double initValue) {
        super(id);
        this.initValue = initValue;
        this.owner = owner;
    }
}
