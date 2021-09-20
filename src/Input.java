import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class Input {
    Logic logic = new Logic();
    private String[] arrayOperands;
    private final ArrayList<Integer> integerArrayOperands = new ArrayList<>();
    private String input;
    private String currentOperation;
    private int countOperation;
    private int countRomanNumber;
    private boolean presenceRomanNumber = false;

    public void input() throws Exception {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine().replace(" ", "");
            //проверяем сколько и какие знаки в выражении
            for (Operation operation : Operation.values()) {
                if (input.contains(operation.getOperation())) {
                    switch (operation) {
                        case ADDITION:
                            amountOperation(input.toCharArray(), operation.getOperation());
                            arrayOperands = input.split("\\+");
                            currentOperation = operation.getOperation();
                            break;
                        case SUBTRACTION:
                            amountOperation(input.toCharArray(), operation.getOperation());
                            arrayOperands = input.split(operation.getOperation());
                            currentOperation = operation.getOperation();
                            break;
                        case MULTIPLICATION:
                            amountOperation(input.toCharArray(), operation.getOperation());
                            arrayOperands = input.split("\\*");
                            currentOperation = operation.getOperation();
                            break;
                        case DIVISION:
                            amountOperation(input.toCharArray(), operation.getOperation());
                            arrayOperands = input.split(operation.getOperation());
                            currentOperation = operation.getOperation();
                            break;
                    }
                }
            }
            //Если знака нет или более 1 в выражении - выкидываем ошибку
            if (currentOperation == null || countOperation > 1) {
                throw new Exception("Строка не является математической операцией");
            }
            //Если количество операнд не равно 2 - выкидываем ошибку
            if (Arrays.stream(arrayOperands).count() != 2
                    || Arrays.stream(arrayOperands).anyMatch(x -> x.length() <= 0)) {
                throw new Exception("Количество операнд не равно 2");
            }
            //Проверяем есть ли римские числа
            Stream.of(RomanNumber.values()).forEach(x -> {
                for (int y = 0; y < arrayOperands.length; y++) {
                    if (x.name().equals(arrayOperands[y])) {
                        arrayOperands[y] = String.valueOf(x.getRomanNumber());
                        countRomanNumber++;
                        presenceRomanNumber = true;
                    }
                }
            });
            for (String operand : arrayOperands) {
                //Если числа содержат дробную часть - выкидываем ошибку
                if (operand.contains(".") || operand.contains(",")) {
                    throw new Exception("Калькулятор умеет работать только с целыми числами");
                }
                try {
                    //Если введены неверные символы или числа не в диапазоне - выкидываем ошибку
                    if (Integer.parseInt(operand) < 1 || Integer.parseInt(operand) > 10) {
                        throw new Exception("Неправильно введены данные для подсчета");
                    }
                } catch (Exception e) {
                    throw new Exception("Неправильно введены данные для подсчета");
                }
            }
            //Проверяем количество операнд римских чисел
            if (presenceRomanNumber && countRomanNumber != 2) {
                throw new Exception("Используются одновременно разные системы счисления");
            }
            for (String operand : arrayOperands) {
                integerArrayOperands.add(Integer.parseInt(operand));
            }
            logic.calculation(integerArrayOperands, currentOperation, presenceRomanNumber);
            clear();
        }
    }

    public void clear() {
        countOperation = 0;
        countRomanNumber = 0;
        currentOperation = null;
        presenceRomanNumber = false;
        integerArrayOperands.clear();
    }

    //Подсчет количества операций в выражении
    public void amountOperation(char[] arrayChars, String currentOperation) {
        for (char c : arrayChars) {
            String arrayVariable = String.valueOf(c);
            if (arrayVariable.equals(currentOperation)) {
                countOperation++;
            }
        }
    }
}