import java.util.*;

public class Calculator {
    // two parameters will work since 3+5+6 = 8+6
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
        System.out.print("Can not divide by zero input new equation");
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

    public static void main(String[] args) {
        // key to this thing will be to have user input entire equation at once and
        // return result from there
        // keeping the calucations going can be a question after result was returned
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Input your number");

        int operand1 = keyboard.nextInt();
        int operand2 = keyboard.nextInt();
        keyboard.nextLine();
        System.out.println(operand1 + " + " + operand2 + " = " + addInteger(operand1, operand2));
        System.out.println(operand1 + " - " + operand2 + " = " + subtractInteger(operand1, operand2));
        System.out.println(operand1 + " * " + operand2 + " = " + multiplyInteger(operand1, operand2));
        System.out.println(operand1 + " / " + operand2 + " = " + divideInteger(operand1, operand2));
        System.out.println(operand1 + " ^ " + operand2 + " = " + solveExponent(operand1, operand2));

        System.out.println("Now input an entire equation..");
        String inputEntireEquation = keyboard.nextLine();

        System.out.println("Now solve this.." + inputEntireEquation);

        keyboard.close();
    }

}