package hello.axon.sample.events;

public class SubstractedEvent extends BaseEvent<String> {
    public final String EVENT_TYPE = "SUBSTRACT";
    public final double num;

    public SubstractedEvent(String id, double num) {
        super(id);

        this.num = num;
    }
}
