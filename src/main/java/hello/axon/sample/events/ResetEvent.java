package hello.axon.sample.events;

public class ResetEvent extends BaseEvent<String> {
    public final String EVENT_TYPE = "RESET";

    public ResetEvent(String id) {
        super(id);
    }
}
