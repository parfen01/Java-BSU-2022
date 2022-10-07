package by.parfen01.quiser.tasks.math_tasks;

import by.parfen01.quiser.Result;

public class EquationTask extends AbstractMathTask {
    int positionOfX;
    String text;
    String answer;
    public EquationTask(int firstNumber, int secondNumber, int positionOfX, Operation operation) {
        super(firstNumber, secondNumber, operation);
        this.positionOfX = positionOfX;
        if (positionOfX != 0 && positionOfX != 1) {
            throw new IllegalArgumentException();
        }
        if (positionOfX == 0) {
            text = "X" + Operation.toChar(operation) + firstNumber + "=" + secondNumber;
            if (firstNumber == 0 && operation == Operation.ADDITION) {
                answer = "invalid operation";
                return;
            }
            answer = String.valueOf(calculate(secondNumber, firstNumber, Operation.getReverseOperation(operation)));
        } else {
            text = firstNumber + Operation.toChar(operation) + "X" + "=" + secondNumber;
            if (firstNumber == 0 && secondNumber != 0 && operation == Operation.ADDITION) {
                answer = "no right answer";
                return;
            }
            if (operation == Operation.ADDITION || operation == Operation.MULTIPLICATION) {
                answer = String.valueOf(calculate(secondNumber, firstNumber, Operation.getReverseOperation(operation)));
            } else {
                answer = String.valueOf(calculate(firstNumber, secondNumber, operation));
            }
        }
    }


    @Override
    public String getText() {
        return text;
    }

    @Override
    public Result validate(String answer) {
        if (answer == null) {
            throw new NullPointerException();
        }
        if (!answer.matches("^-?[0-9]+$") &&
                !answer.equalsIgnoreCase("invalid operation") &&
                !answer.equalsIgnoreCase("no right answer")) {
            return Result.INCORRECT_INPUT;
        }
        if (answer.equals("-0") && this.answer.equals("0")) {
            return Result.OK;
        }
        return this.answer.equals(answer) ? Result.OK : Result.WRONG;
    }
}