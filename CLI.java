package class_example;
import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {
        command = new Scanner(System.in);
        boolean running = true;
        Calculator calc = new Calculator();

        print_program_details();
        while (running == true) {
            String user_equation = get_user_input(command);
            if(user_equation.equals("exit")) {
                running = false;
                command.close();
            }
            msg("The expression is " +calc.evaluate(user_equation));
        }
    }
    // Global variable to hold the current progress of the equation.
    public static String[] equation;
    public static Scanner command;

    // Returns user input, as text, from the console.
    private static String get_user_input(Scanner command) {
        msg("Input the mathematical equation you would like solved: ");
        return command.nextLine();
    }

    // Uses message to print the program details.
    private static void print_program_details() {
        msg("\nThis program is a Calculator and is used to solve mathematical expressions.");
        msg("You must use a space after each number for operations.\n");
    }

    // Method to handle and shorten System.out.println.
    private static void msg(String message) {
        System.out.println(message);
    }
}