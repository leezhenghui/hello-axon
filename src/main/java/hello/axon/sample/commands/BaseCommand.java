package hello.axon.sample.commands;

import org.springframework.util.Assert;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

public class BaseCommand<T> {

    @TargetAggregateIdentifier
    public final T id;

    public BaseCommand(T id) {
        Assert.notNull(id, "Invalid command id");
        this.id = id;
    }

    public T getId() {
        return this.id;
    }
}
