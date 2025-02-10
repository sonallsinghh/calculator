package org.example;
import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;

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
        Stack<Double> operands = new Stack<>();
        Stack<String> operators = new Stack<>();

        StringTokenizer tokenizer = new StringTokenizer(expression, "+-*/() ", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            if (token.isEmpty()) continue;

            if (Character.isDigit(token.charAt(0))) {
                operands.push(Double.parseDouble(token));
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.peek().equals("(")) {
                    applyOperator(operands, operators);
                }
                operators.pop();
            } else if ("+-*/".contains(token)) {
                while (!operators.isEmpty() && hasPrecedence(token, operators.peek())) {
                    applyOperator(operands, operators);
                }
                operators.push(token);
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        while (!operators.isEmpty()) {
            applyOperator(operands, operators);
        }

        return operands.pop();
}

    private static boolean hasPrecedence(String op1, String op2) {
        if (op2.equals("(") || op2.equals(")"))
            return false;
        return (op1.equals("*") || op1.equals("/")) &&
                (op2.equals("+") || op2.equals("-"));
    }

    private static void applyOperator(Stack<Double> operands, Stack<String> operators) {
        double operand2 = operands.pop();
        double operand1 = operands.pop();
        String operator = operators.pop();

        switch (operator) {
            case "+" -> operands.push(operand1 + operand2);
            case "-" -> operands.push(operand1 - operand2);
            case "*" -> operands.push(operand1 * operand2);
            case "/" -> {
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                operands.push(operand1 / operand2);
            }
            default -> throw new IllegalArgumentException("Invalid operator");
        }
    }
    }