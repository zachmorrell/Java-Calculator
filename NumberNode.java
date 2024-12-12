package class_example;

public class NumberNode implements ENode {
    private double value;

    public NumberNode(double n) {
        value = n;
    }
    @Override
    public double getValue() {
        return value;
    }
}
