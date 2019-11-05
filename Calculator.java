import java.util.*;

public class Calculator {

    static int addInteger(int firstNumber, int secondNumber) {

        return firstNumber + secondNumber;
    }

    static int subtractInteger(int firstNumber, int secondNumber) {

        return firstNumber - secondNumber;
    }

    static int multiplyInteger(int firstNumber, int secondNumber) {

        return firstNumber * secondNumber;
    }

    static int divideInteger(int firstNumber, int secondNumber) {
        if (secondNumber != 0) {
            return firstNumber / secondNumber;
        }
        System.out.print("\nCan not divide by zero input new equation");
        return 0;
    }

    static int solveExponent(int base, int exponent) {
        if (exponent == 0) {

            return 1;
        }
        int product = base;
        while (exponent > 1) {
            product *= base;
            exponent--;

        }

        return product;
    }

    // Higher returned value means higher precedence
    static int DefinePrecedence(String x) {
        switch (x) {
        case "+":
        case "-":
            return 1;

        case "*":
        case "/":
            return 2;

        case "^":
            return 3;
        }
        return -1;
    }

    static int recognizeOperator(String operator, int firstNumber, int secondNumber) {

        int solution = 0;
        switch (operator) {
        case "+":
            solution = addInteger(firstNumber, secondNumber);
            break;
        case "-":
            solution = subtractInteger(firstNumber, secondNumber);
            break;

        case "*":
            solution = multiplyInteger(firstNumber, secondNumber);
            break;
        case "/":
            solution = divideInteger(firstNumber, secondNumber);
            break;

        case "^":
            solution = solveExponent(firstNumber, secondNumber);
            break;
        }

        return solution;

    }

    static void printArray(ArrayList<String> arr) {
        for (int i = 0; i < arr.size(); i++) {
            System.out.print(arr.get(i) + " ");
        }

    }

    static ArrayList<String> parseEquation(String x) {

        String parsedS = "";

        int stringLength = x.length();

        ArrayList<String> arr = new ArrayList<String>();

        for (int i = 0; i < x.length(); i++) {

            if (Character.isLetterOrDigit(x.charAt(i))) {
                parsedS += x.charAt(i);

            } else if (x.charAt(i) == '+' || x.charAt(i) == '-' || x.charAt(i) == '/' || x.charAt(i) == '*'
                    || x.charAt(i) == '^') {

                if (!parsedS.equals("")) {
                    arr.add(parsedS);
                }

                parsedS = "";

                parsedS += x.charAt(i);
                arr.add(parsedS);

                parsedS = "";

            } else if (x.charAt(i) == '(') {
                parsedS += x.charAt(i);
                arr.add(parsedS);
                parsedS = "";

            } else if (x.charAt(i) == ')') {
                arr.add(parsedS);
                parsedS = "";
                parsedS += x.charAt(i);
                arr.add(parsedS);
                parsedS = "";

            } else {
                System.out.print("Unknown symbol");
            }

        }

        if (!parsedS.equals("")) {
            arr.add(parsedS);
        } // this gets last number in since there is no operator after it

        return arr;
    }

    static ArrayList<String> infixToPostfix(String infixEquation) {

        String postfixExpression = "";

        Stack<String> stack = new Stack<>();

        ArrayList<String> arr = parseEquation(infixEquation);

        ArrayList<String> arrPost = new ArrayList<String>();

        String popped = "";

        for (int i = 0; i < arr.size(); ++i) {

            String c = arr.get(i);

            // If the scanned character is an operand, add it to output.
            if (!c.equals("+") && !c.equals("-") && !c.equals("*") && !c.equals("/") && !c.equals("^") && !c.equals("(")
                    && !c.equals(")")) {

                arrPost.add(c);

            // If the scanned character is an '(', push it to the stack.
            } else if (c.equals("(")) {
                stack.push(c);

                // If the scanned character is an ')', pop and output from the stack
                // until an '(' is encountered.
            } else if (c.equals(")")) {

                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    popped = stack.pop();

                    arrPost.add(popped);
                }

                if (!stack.isEmpty() && !stack.peek().equals("(")) {

                    return arr;

                } else {
                    stack.pop();
                }

            } else // an operator is encountered
            {

                while (!stack.isEmpty() && DefinePrecedence(c) <= DefinePrecedence(stack.peek())) {
                    if (stack.peek().equals("(")) {
                        // incorrect parenthesis
                        return arr;
                    }

                    popped = stack.pop();

                    arrPost.add(popped);
                }
                stack.push(c);
            }

        }

        // pop all the operators from the stack
        while (!stack.isEmpty()) {
            if (stack.peek().equals("(")) {

                return arr;
            }

            popped = stack.pop();

            arrPost.add(popped);
        }

        return arrPost;

    }

    static int evaluatePostfix(ArrayList<String> postfixExpression) {
        // if next char is an operand push to stack if char is an operator
        // pop two most recent numbers and call method to evaluate equation based
        // on what operator it is then push back to stack

        // initializing empty stack
        Stack<Integer> stack = new Stack<>();

        int operand1 = 0;
        int operand2 = 0;
        int result = 0;

        for (int i = 0; i < postfixExpression.size(); i++) {

            String character = postfixExpression.get(i);

            if (!character.equals("+") && !character.equals("-") && !character.equals("*") && !character.equals("/")
                    && !character.equals("^")) {

                stack.push(Integer.valueOf(character));

            } else {

                operand1 = stack.pop();

                operand2 = stack.pop();

                result = recognizeOperator(character, operand2, operand1);

                stack.push(result);
            }

        }

        return stack.pop();

    }

    public static boolean checkForCorrectNumberOfOperators(String equation) {

        for (int i = 0; i < equation.length() - 1; i++) {

            if ((equation.charAt(i) == '+' || equation.charAt(i) == '-' || equation.charAt(i) == '*'
                    || equation.charAt(i) == '/' || equation.charAt(i) == '^')
                    && (equation.charAt(i + 1) == '+' || equation.charAt(i + 1) == '-' || equation.charAt(i + 1) == '*'
                            || equation.charAt(i + 1) == '/' || equation.charAt(i + 1) == '^')) {

                System.out.println("\nToo many operators");
                return false;
            }

        }
        return true;
    }

    public static boolean equationStartAndFinish(String equation) {

        if (Character.isDigit(equation.charAt(0)) || equation.charAt(0) == '(') {

            if (Character.isDigit(equation.charAt(equation.length() - 1))
                    || equation.charAt(equation.length() - 1) == ')') {
                return true;
            }

        }

        System.out.println("Equations should start with parenthesis ( or positive numbers and end with a number");
        return false;
    }

    public static boolean onlyDigits(String equation) {

        for (int i = 0; i < equation.length(); i++) {

            if (!Character.isDigit(equation.charAt(i)) && !(equation.charAt(i) == '+' || equation.charAt(i) == '-'
                    || equation.charAt(i) == '*' || equation.charAt(i) == '/' || equation.charAt(i) == '^'
                    || equation.charAt(i) == '(' || equation.charAt(i) == ')')) {

                System.out.println(equation.charAt(i));
                System.out.println("Equation should only contain integer values with no spaces");
                return false;

            }

        }

        return true;

    }

    static boolean checkPar(String equation) {

        int openPar = 0;
        int closedPar = 0;
        for (int i = 0; i < equation.length(); i++) {
            if (equation.charAt(i) == '(') {
                openPar++;

            } else if (equation.charAt(i) == ')') {
                closedPar++;
            }

        }

        if (openPar != closedPar) {

            System.out.println("Each open parenthesis should have a closing parenthesis");
        }
        return openPar == closedPar;

    }

    public static void main(String[] args) {
        // key to this thing will be to have user input entire equation at once and
        // return postfixExpression from there
        // keeping the calucations going can be a question after postfixExpression was
        // returned
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Input an entire equation..");
        String inputEntireEquation = keyboard.nextLine();

        System.out.println("Infix Notation: " + inputEntireEquation);

        // this for loop can have all error checking
        if (checkForCorrectNumberOfOperators(inputEntireEquation) && equationStartAndFinish(inputEntireEquation)
                && checkPar(inputEntireEquation) && onlyDigits(inputEntireEquation)) {
            ArrayList<String> arr = parseEquation(inputEntireEquation);
            System.out.println(arr);
            ArrayList<String> arrPost = infixToPostfix(inputEntireEquation);

            System.out.print("Postfix Notation: ");
            printArray(arrPost);
            int solution = 0;
            solution = evaluatePostfix(arrPost);

            System.out.println("\nSolution " + solution);

        }

        keyboard.close();
    }

}
