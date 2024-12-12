package class_example;

import java.util.Arrays;

public class Calculator {
    // Stacks used for building the order of operations.
        private Stack<String> operator_stack;
        private Stack<ENode> operand_stack;

    // Returns true is the operator is supported.
    private boolean isValidOperator(String operator) {
        String[] whitelist = {"+", "-", "*", "/", "^"};
        return Arrays.asList(whitelist).contains(operator);
    }
    // Returns true if the value passed can be converted to a double.
    private boolean isValidOperand(String number) {
        try {
            Double.valueOf(number);
        } catch(Exception e) { return false; }
        return true;
    }
    // Assigns an order of operations value per supported operator.
    private int priority(String s) {
        if (s.equals("+") || s.equals("-")) {return 0;}
        else if(s.equals("*") || s.equals("/")) { return 1;}
        else if(s.equals("^")) { return 3;}
        return -1;
    }
    
    public boolean valid_period(String s) {
        for(int i = s.length()-1; i > 0; i--) {
            if(s.charAt(i) == ' ') { break; }
            if (s.charAt(i) == '.') { return false; }
        }
        return true;
    }

    public String evaluate(String expr) {
        // Build the expression tree, return the root enode
        ENode results = buildExpressionTree(expr);
        if(results == null) {
            return "Invalid Expression";
        }
        // Return the value of the root ENode.
        return String.valueOf(results.getValue());
    }

    // Uses recursion to return the root Enode of an expression.
    private ENode buildExpressionTree(String expr) {
        String[] expr_array = expr.split(" ");
        //shunting yeard

        // Check if length is even, if it is then return null.
        if(expr_array.length % 2 == 0) { return null; }

        // Create an operator and operand stack.
        operator_stack = new Stack<>();
        operand_stack = new Stack<>();

        // Loop through the expression building the stacks.
        for(int i = 0; i < expr_array.length; i++) {
            String _index = expr_array[i];
            if(i%2 == 0) {
            // Even: shall be operand.
                if(!isValidOperand(_index)) { return null; }
                // Check if stack is empty, if so add to top of stack.
                NumberNode number_node = new NumberNode(Double.parseDouble(_index));
                operand_stack.push(number_node);
            } else {

                // Else i is odd so _index is an operator.
                if(!isValidOperator(_index)) { return null; }

                // Get the priority value, if priority is -1 return null.
                int priority = priority(_index);
                if(priority == -1) { return null; }

                // If operator stack is empty, push operator onto stack.
                if(operator_stack.isEmpty()) { operator_stack.push(_index); }

                else {
                    boolean priority_check = true;
                    while (priority_check) {                      
                        String top = operator_stack.peek();
                        if(top != null && priority(top) >= priority) {
                            build_OperatorNode();
                        } else {
                            priority_check = false;
                        }
                    }
                    operator_stack.push(_index); 
                }
            }
        }
        
        
        while (!operator_stack.isEmpty()) {
            if(operand_stack.isEmpty()) { return null;}
            operand_stack.getSize();
            build_OperatorNode();
        }

        // After emptying the operator stack, if operand stack is larger than 1.. invalid expression.
        if(operand_stack.getSize() != 1) { return null; }

        return operand_stack.peek();
    }

    private void build_OperatorNode() {
        OperatorNode new_node = new OperatorNode(operator_stack.pop());
        new_node.setRight(operand_stack.pop());
        new_node.setLeft(operand_stack.pop());
        operand_stack.push(new_node);
    }
}