package hello.axon.sample.aggregate;

import hello.axon.sample.commands.*;
import hello.axon.sample.events.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.Serializable;

@Aggregate(repository = "calculatorRepository")
public class CalculatorAggregate implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(CalculatorAggregate.class);
    private static final long serialVersionUID;

    static {
        serialVersionUID = 1L;
    }

    @AggregateIdentifier
    private String id;

    private double value;
    private String owner;

    public CalculatorAggregate() {
    }

    @CommandHandler
    public CalculatorAggregate(CreateCalculatorCommand ccc) {
        Assert.notNull(ccc.id, "Invalid id in createCalculator command");
        Assert.notNull(ccc.owner, "Invalid owner value in createCalculator command");
        AggregateLifecycle.apply(new CalculatorCreatedEvent(ccc.getId(), ccc.getOwner(), ccc.getInitValue()));
    }

    @CommandHandler
    protected void on(DestroyCalculatorCommand dcc) {
        Assert.notNull(dcc.id, "Invalid id in destroyCalculator command");
        AggregateLifecycle.apply(new CalculatorDestroyedEvent(dcc.getId()));

    }

    @CommandHandler
    protected void on(AddCommand ac) {
        Assert.notNull(ac.getId(), "Invalid id in add command");
        AggregateLifecycle.apply(new AddedEvent(ac.getId(), ac.getNum()));
    }

    @CommandHandler
    protected void on(SubstractCommand sc) {
        Assert.notNull(sc.getId(), "Invalid id in substract command");
        AggregateLifecycle.apply(new SubstractedEvent(sc.getId(), sc.getNum()));
    }

    @CommandHandler
    protected void on(ResetCommand rc) {
        Assert.notNull(rc.getId(), "Invalid id in reset command");
        AggregateLifecycle.apply(new ResetEvent(rc.getId()));
    }

    @EventSourcingHandler
    protected void on(CalculatorCreatedEvent cce) {
        this.id = cce.id;
        this.owner = cce.owner;
        this.value = cce.initValue;
    }

    @EventSourcingHandler
    protected void on(CalculatorDestroyedEvent dce) {
        AggregateLifecycle.markDeleted();
    }

    @EventSourcingHandler
    protected void on(AddedEvent ae) {
        this.value += ae.num;
    }

    @EventSourcingHandler
    protected void on(SubstractedEvent se) {
        this.value -= se.num;
    }

    @EventSourcingHandler
    protected void on(ResetEvent re) {
        this.value = 0;
    }

    public double getResult() {
        LOG.info("Result: {}", this.value);
        return this.value;
    }
}
