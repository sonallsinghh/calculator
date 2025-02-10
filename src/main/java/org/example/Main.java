package org.example;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter an expression (or type 'exit'): ");
            String expression = scanner.nextLine();

            if (expression.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                double result = evaluate(expression);
                System.out.println(expression + " = " + result);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    public static double evaluate(String expression) {
        // Very basic evaluation (only handles a single operation)
        String[] parts = expression.split(" "); // Split by spaces

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid expression"); // Must be like "2 + 3"
        }

        double operand1 = Double.parseDouble(parts[0]);
        String operator = parts[1];
        double operand2 = Double.parseDouble(parts[2]);

        return switch (operator) {
            case "+" -> operand1 + operand2;
            case "-" -> operand1 - operand2;
            case "*" -> operand1 * operand2;
            case "/" -> {  
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield operand1 / operand2; 
            } 
            default -> throw new IllegalArgumentException("Invalid operator");
        };
    }
}
