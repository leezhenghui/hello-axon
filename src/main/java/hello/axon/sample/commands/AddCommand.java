package hello.axon.sample.commands;

public class AddCommand extends BaseCommand<String> {

    private final double num;

    public AddCommand(String id, double num) {
        super(id);
        this.num = num;
    }

    public double getNum() {
        return this.num;
    }
}
