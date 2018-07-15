package hello.axon.sample.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import hello.axon.sample.aggregate.CalculatorAggregate;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventhandling.EventProcessor;
import org.axonframework.eventhandling.TrackedEventMessage;
import org.axonframework.eventsourcing.*;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.kafka.eventhandling.DefaultKafkaMessageConverter;
import org.axonframework.kafka.eventhandling.KafkaMessageConverter;
import org.axonframework.kafka.eventhandling.consumer.Fetcher;
import org.axonframework.kafka.eventhandling.consumer.KafkaMessageSource;
import org.axonframework.messaging.StreamableMessageSource;
import org.axonframework.mongo.DefaultMongoTemplate;
import org.axonframework.mongo.MongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.MongoFactory;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Iterator;

@Configuration
public class CalculatorAggregateConfig {

    private final static int SNAPSHOT_TRIGGER_THRESHOLD = 1;


    @Value("${mongodb.url}")
    private String mongodbHost;

    @Value("${mongodb.port}")
    private int mongodbPort;

    @Value("${mongodb.dbname}")
    private String mongodbName;

    @Bean
    public MongoClient mongoClient(){
        MongoFactory mongoFactory = new MongoFactory();
        mongoFactory.setMongoAddresses(Arrays.asList(new ServerAddress(mongodbHost, mongodbPort)));
        return mongoFactory.createMongo();
    }

    @Bean(name = "axonMongoTemplate")
    public MongoTemplate axonMongoTemplate() {
        MongoTemplate template = new DefaultMongoTemplate(mongoClient(), mongodbName);
        return template;
    }

    /**
     * Event Store(Event Bus) configuration
     *
     * @return
     */
    @Bean
    public EventStorageEngine eventStore() {
        return new MongoEventStorageEngine(axonMongoTemplate());
    }

    @Bean
    public SpringAggregateSnapshotterFactoryBean snapshotterFactoryBean() {
        return new SpringAggregateSnapshotterFactoryBean();
    }

    /**
     * Snapsotter enabled event sourcing aggregate repository
     *
     * @param eventStore
     * @param snapshotter
     * @return
     */
    @Bean
    public EventSourcingRepository<CalculatorAggregate> calculatorRepository(EventStore eventStore, Snapshotter snapshotter) {
        return new EventSourcingRepository<CalculatorAggregate>(
                CalculatorAggregate.class,
                eventStore,
                new EventCountSnapshotTriggerDefinition(snapshotter, SNAPSHOT_TRIGGER_THRESHOLD)
        );
    }

    @Bean
    public KafkaMessageConverter<String, byte[]> kafkaMessageConverter(@Qualifier("eventSerializer") Serializer eventSerializer) {
        return new DefaultKafkaMessageConverter(eventSerializer);
    }

    @Bean
    public KafkaMessageSource<String, byte[]> kafkaMessageSource(Fetcher<String, byte[]> kafkaFetcher) {
        return new KafkaMessageSource<>(kafkaFetcher);
    }
}
