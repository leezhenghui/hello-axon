package hello.axon.sample.commands;

public class DestroyCalculatorCommand extends BaseCommand<String> {

    public DestroyCalculatorCommand(String id) {
        super(id);
    }
}
