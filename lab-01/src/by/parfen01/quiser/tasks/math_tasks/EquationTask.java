package by.parfen01.quiser.tasks.math_tasks;

import by.parfen01.quiser.Result;

import java.util.EnumSet;

public class EquationTask extends AbstractMathTask {
    private final String text;
    private final String answer;
    public EquationTask(double firstNumber, double secondNumber, int precision, int positionOfX, Operation operation) {
        super(firstNumber, secondNumber, precision, operation);
        if (positionOfX != 0 && positionOfX != 1) {
            throw new IllegalArgumentException();
        }
        if (positionOfX == 0) {
            text = "The precision of answer must be no less then " + getPrecisionAsDoubleValue() + "\n" +
                    "X" + Operation.toChar(operation) + firstNumber + "=" + secondNumber;
            if (doubleEqual(firstNumber, 0.0) && operation == Operation.DIVISION) {
                answer = "invalid operation";
                return;
            }
            if (doubleEqual(firstNumber, 0.0) &&
                    doubleEqual(secondNumber, 0.0) &&
                    operation == Operation.MULTIPLICATION) {
                answer = "no right answer";
                return;
            }
            answer = String.valueOf(calculate(secondNumber, firstNumber, Operation.getReverseOperation(operation)));
        } else {
            text = "The precision of answer must be no less then " + getPrecisionAsDoubleValue() + "\n" +
                    firstNumber + Operation.toChar(operation) + "X" + "=" + secondNumber;
            if (doubleEqual(firstNumber, 0) &&
                    doubleEqual(secondNumber, 0.0) &&
                    (operation == Operation.MULTIPLICATION || operation == Operation.DIVISION)) {
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
        if (!answer.matches("^-?[0-9]+(\\.[0-9]+)?$") &&
                !answer.equalsIgnoreCase("invalid operation") &&
                !answer.equalsIgnoreCase("no right answer")) {
            return Result.INCORRECT_INPUT;
        }

        return checkAnswer(this.answer, answer);
    }

    public static class Generator extends AbstractMathTask.Generator {
        private final int[] possiblePositionsOfX;
        /**
         * @param minNumber              минимальное число
         * @param maxNumber              максимальное число
         */

        public Generator(double minNumber,
                         double maxNumber,
                         EnumSet<Operation> operations,
                         int[] possiblePositionsOfX) {
            this(minNumber, maxNumber, 0, operations, possiblePositionsOfX);
        }
        public Generator(double minNumber,
                                     double maxNumber,
                                     int precision,
                                     EnumSet<Operation> operations,
                                     int[] possiblePositionsOfX) {
            super(minNumber, maxNumber, precision, operations);
            this.possiblePositionsOfX = possiblePositionsOfX.clone();
        }

        /**
         * return задание типа {@link EquationTask}
         */
        public EquationTask generate() {
            int xPos = possiblePositionsOfX[(int)(Math.random() * possiblePositionsOfX.length)];
            return new EquationTask(getRandomNumberForTask(), getRandomNumberForTask(),
                    getPrecision(), xPos, getRandomOperationFromSet());
        }
    }
}