package hello.axon.sample.commands;

public class SubstractCommand extends BaseCommand<String> {

    private final double num;

    public SubstractCommand(String id, double num) {
        super(id);
        this.num = num;
    }

    public double getNum() {
        return this.num;
    }
}
