package hello.axon.sample.controllers;

import hello.axon.sample.commands.*;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequestMapping("/calculator")
@RestController
public class ApiController {
    private static final Logger LOG = LoggerFactory.getLogger(ApiController.class);

    private CommandGateway cmdGw;
    private EventStore eventStore;

    public ApiController(CommandGateway cg, EventStore es) {
        this.cmdGw = cg;
        this.eventStore = es;
    }

    @GetMapping("{id}/events")
    public List<Object> listEvents(@PathVariable String id) {
        LOG.info("List events");
        return eventStore.readEvents(id).asStream().map(s -> s.getPayload()).collect(Collectors.toList());
    }

    private static class CalculatorStruct {
        public String owner;
        public int initValue;
    }

    @PostMapping
    public CompletableFuture<String> createCalculator(@RequestBody CalculatorStruct cs) {
        LOG.info("Create calculator");
        String id = UUID.randomUUID().toString();
        return cmdGw.send(new CreateCalculatorCommand(id, cs.owner, cs.initValue));
    }

    @PutMapping(path = "{id}/add")
    public CompletableFuture<String> add(@PathVariable String id, @RequestBody double num) {
        LOG.info("Add operation");
        return cmdGw.send(new AddCommand(id, num));
    }

    @PutMapping(path = "{id}/sub")
    public CompletableFuture<String> sub(@PathVariable String id, @RequestBody double num) {
        LOG.info("Substract operation");
        return cmdGw.send(new SubstractCommand(id, num));
    }

    @PutMapping(path = "{id}/reset")
    public CompletableFuture<String> reset(@PathVariable String id) {
        LOG.info("Reset operation");
        return cmdGw.send(new ResetCommand(id));
    }

    @DeleteMapping(path = "{id}")
    public CompletableFuture<String> destroyCalculator(@PathVariable String id) {
        LOG.info("Destroy calculator");
        return cmdGw.send(new DestroyCalculatorCommand(id));
    }

}
