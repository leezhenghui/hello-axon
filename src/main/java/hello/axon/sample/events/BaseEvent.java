package hello.axon.sample.events;

import org.springframework.util.Assert;

public class BaseEvent<T> {

    public final T id;

    public BaseEvent(T id) {
        Assert.notNull(id, "Invalid event id parameter");
        this.id = id;
    }
}
