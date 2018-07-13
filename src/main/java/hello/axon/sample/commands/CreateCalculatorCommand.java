package hello.axon.sample.commands;

public class CreateCalculatorCommand extends BaseCommand<String> {
    public final String owner;
    public final int initValue;

    public CreateCalculatorCommand(String id, String owner, int initValue) {
        super(id);
        this.owner = owner;
        this.initValue = initValue;
    }

    public String getOwner() {
        return this.owner;
    }

    public int getInitValue() {
        return this.initValue;
    }
}
