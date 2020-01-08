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

    // The higher the number returned the higher the precedence
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
        return -1;// symbol not recognized
    }

    // solves equation based on operator
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

    // breaks up equation and stores it in arrayList for easier reading
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

    // method converts an infix equation stored in an ArrayList to postfix
    static ArrayList<String> infixToPostfix(String infixEquation) {

        String postfixExpression = "";

        Stack<String> stack = new Stack<>();

        ArrayList<String> arr = parseEquation(infixEquation);// infix

        ArrayList<String> arrPost = new ArrayList<String>();// postfix

        String popped = "";

        for (int i = 0; i < arr.size(); ++i) {

            String c = arr.get(i);

            // If element is an operand(number) add to ArrayList
            if (!c.equals("+") && !c.equals("-") && !c.equals("*") && !c.equals("/") && !c.equals("^") && !c.equals("(")
                    && !c.equals(")")) {

                arrPost.add(c);

                // If the element is an open parenthesis push to stack
            } else if (c.equals("(")) {
                stack.push(c);

                // If the element is a closed parethesis pop stack until a closed parethesis is
                // found
            } else if (c.equals(")")) {

                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    popped = stack.pop();

                    arrPost.add(popped);
                }

                if (!stack.isEmpty() && !stack.peek().equals("(")) {

                    return arr;// incorrect number of parenthesis

                } else {
                    stack.pop();
                }

            } else {
                // an operator encountered
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

        // since we are using postfix the operator left in stack need to be popped
        // out and added to final ArrayList
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
        // if next element is an operand push to stack if element is an operator
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

        Scanner keyboard = new Scanner(System.in);

        System.out.println("Input your equation..");
        String inputEntireEquation = keyboard.nextLine();

        System.out.println("Infix Notation: " + inputEntireEquation);

        // error checking
        if (checkForCorrectNumberOfOperators(inputEntireEquation) && equationStartAndFinish(inputEntireEquation)
                && checkPar(inputEntireEquation) && onlyDigits(inputEntireEquation)) {

        try{

            ArrayList<String> arr = parseEquation(inputEntireEquation);

            ArrayList<String> arrPost = infixToPostfix(inputEntireEquation);

            System.out.print("Postfix Notation: ");
            printArray(arrPost);
            int solution = 0;
            solution = evaluatePostfix(arrPost);

            System.out.println("\nSolution " + solution);

        }catch(Exception e){

            System.out.println("Operation not recognized");
        }
            

        }

        keyboard.close();
    }

}
