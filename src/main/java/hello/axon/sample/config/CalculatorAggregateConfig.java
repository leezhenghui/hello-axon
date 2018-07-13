package hello.axon.sample.config;

import com.mongodb.MongoClient;
import hello.axon.sample.aggregate.CalculatorAggregate;
import org.axonframework.eventsourcing.*;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalculatorAggregateConfig {

    @Bean
    public EventStorageEngine eventStore(MongoClient mClient) {
        return new MongoEventStorageEngine(new DefaultMongoTemplate(mClient));
    }

    @Bean
    public AggregateFactory<CalculatorAggregate> calculatorAggregateFactory() {
        return new GenericAggregateFactory<CalculatorAggregate>(CalculatorAggregate.class);
    }

    @Bean
    public SpringAggregateSnapshotterFactoryBean snapshotterFactoryBean() {
        return new SpringAggregateSnapshotterFactoryBean();
    }

    @Bean
    public EventSourcingRepository<CalculatorAggregate> calculatorRepository(EventStore eventStore, Snapshotter snapshotter) {
        return new EventSourcingRepository<CalculatorAggregate>(CalculatorAggregate.class, eventStore, new EventCountSnapshotTriggerDefinition(snapshotter, 1));
    }
}
