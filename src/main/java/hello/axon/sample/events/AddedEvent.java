package hello.axon.sample.events;

public class AddedEvent extends BaseEvent<String> {
    public final String EVENT_TYPE = "ADD";
    public double num;

    public AddedEvent(String id, double num) {
        super(id);
        this.num = num;
    }
}
