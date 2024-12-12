package class_example;

public class OperatorNode implements ENode {
    private String operator;
    private ENode left, right;

    public String getOperator() {
        return operator;
    }

    public ENode getLeft() {
        return left;
    }

    public void setLeft(ENode left) {
        this.left = left;
    }

    public ENode getRight() {
        return right;
    }

    public void setRight(ENode right) {
        this.right = right;
    }

    public OperatorNode(String op) {
        operator = op;
        left = null;
        right = null;
    }

    public double getValue() {
        /*
         * Get left value, then get right, and perform operation on the numbers
         * If there is an operator, move down.
         */
        switch (operator) {
            case "-":
                return left.getValue() - right.getValue();
            case "+":
                return left.getValue() + right.getValue();
            case "/":
                return left.getValue() / right.getValue();
            case "*":
                return left.getValue() * right.getValue();
            case "^":
                return Math.pow(left.getValue(), right.getValue());
        }
        throw new UnsupportedOperationException("Currently not implemented.");
    }
}