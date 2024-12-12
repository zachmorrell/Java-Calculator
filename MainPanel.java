package class_example;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class MainPanel extends JPanel {

        // Instance of GUICalculator for passing focus.
        GUICalculator guiCalculator;
        Calculator calc;

        // Arrays for the button labels and buttons.
        final String[] button_labels = {"AC", "backspace", "divide", "multiply", "7", "8", "9", "subtract", "4", "5", "6", "add", "1", "2", "3", "power", "00", "0", "period", "equals" };
        JButton[] button = new JButton[button_labels.length];

        // Expression string and label.
        String expression = "0";
        JLabel gui_expression = new JLabel(expression);

        GridBagConstraints gbc;

    MainPanel(GUICalculator guiCalculator) {

        // Set the instance of the GUICalculator to pass focus for the key listener.
        this.guiCalculator = guiCalculator;
        calc = new Calculator();

        // Set and initialize layout properties.
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add the GUI expression label to the JPanel using Grid Bag Constraints.
        add_gbc(gui_expression, 0, 0, 4);
        gui_expression.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        gui_expression.setForeground(Color.darkGray);

        // Initialize the button listener class defined below.
        ButtonListener buttonListener = new ButtonListener();

        /*
         Loop through the button array:
            Initialize buttons with the text being equal to the labels.
            Add action listeners to the buttons.
            Add to the JPanel using grid bag constraints.
        */
        for (int i = 0; i < button.length; i++){
            Image icon;
            try {
                System.out.println(button_labels[i]);
                icon = ImageIO.read(getClass().getResource("assets/img/"+button_labels[i]+".png"));
                button[i] = new JButton(new ImageIcon(icon));
                button[i].setActionCommand(button_labels[i]);
                button[i].setBackground(Color.YELLOW);
            } catch (Exception ex) {
                System.out.println("Image Not found.");
            }
            button[i].addActionListener(buttonListener);
            add_gbc(button[i], get_column(i), get_row(i+1), 1);
        }
    }

    // Returns the row for a button.
    private int get_row(int i) {
        // i/4 rounded up.
        return (int) Math.ceil((double) i / 4);
    }
    
    // Returns the column for a button.
    private int get_column(int i){
        return i%4;
    }

    // Applies the gbc constraints to a given component and adds it to the panel.
    public void add_gbc(Component component, int x, int y, int width) {
        gbc.gridwidth = width;
        gbc.gridx = x;
        gbc.gridy = y;
        add(component, gbc);
    }

    // Modifies the expression based on button or key pressed.
    public void handle_button_press(String s) {
        try {
            switch (s) {
                // Backspace
                case "<=":
                case "backspace":
                    backspace();               
                    break;
                // All Clear
                case "AC":
                    expression = "0";
                    break;
                // Operators
                case "divide":
                case "multiply":
                case "subtract":
                case "add":
                case "power":
                case "/":
                case "*":
                case "-":
                case "+":
                case "^":
                    handle_operator(s);
                    break;
                // Equals
                case "=":
                case "equals":
                    calculate_expression();
                    break;
                // Zero handling, not supporting doubles currently and 10 + 01 is annoying to look at.
                case "0":
                case "00":
                    if (!last_char_space()) {
                        expression += (expression.equals("0") ? "" : s);
                    }
                    break;
                case ".":
                    if(calc.valid_period(expression)) {
                        expression += ".";
                    }
                break;
                default:
                    if(!isNumeric(s)) {
                        System.out.println("Not a number");
                        break;
                    }
                    expression = (expression.equals("0") ? s : expression + s);
            }
            gui_expression.setText(expression);
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    private void handle_operator(String s) {
        // If a space is present than an operator is present, remove it.
        HashMap<String, String> operators = new HashMap<String, String>() {{
        put("divide", "/");
        put("multiply", "*");
        put("subtract", "-");
        put("add", "+");
        put("power", "^");
    }};
        if(last_char_space()) {
            backspace();
        }
        s = (s.length() > 1) ? operators.get(s) : s;
        expression += " " + s + " ";
    }

    // Removes the last number or operator used.
    private void backspace() {
        if(expression.length() > 1) {
            // If last action was an operator, then places = 3(operator) , else 1(number).
            int places = (last_char_space()) ? 3 : 1;
            // Substring to remove the number places from the string.
            expression = expression.substring(0, expression.length() - places);
        } else {
            expression = "0";
        }
    }

    // Will eventually handle calculating the expression.
    private void calculate_expression() {
        /*String expression_validity = (calc.isValidExpression(expression.split(" "))) ? "VALID" : "INVALID";        
        System.out.println("The expression is " + expression_validity);
        System.out.println(expression);*/
        String results = calc.evaluate(expression);
        expression = results;
        System.out.println(results);
    }

    // Returns true if the last character of the expression is a space (operator used last).
    private boolean last_char_space() {
        String last_char = String.valueOf(expression.charAt(expression.length()-1));
        return last_char.equals(" ");
    }

    // Used for input validation on keyboard presses.
    private boolean isNumeric(String s) {
        try {
            Integer.valueOf(s);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    // Class used to handle the buttons pressed.
    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            handle_button_press(e.getActionCommand());
            // Give focus back to the GUICalculator instance so it'll continue to handle key presses.
            guiCalculator.getFocus();
        }
    }
}