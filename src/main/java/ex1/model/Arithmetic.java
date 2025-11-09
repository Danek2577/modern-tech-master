package ex1.model;

import common.Model;
import static common.Model.IO;

import java.sql.Connection;

public class Arithmetic extends Model {
    @Override
    public String getDescribeMessage() {
        return "Модель арифметических операций";
    }

    @Override
    public void showCommands() {
        IO.println("\nДоступные команды:");
        IO.println("1. Вывести список таблиц из MySQL.");
        IO.println("2. Создать новую таблицу в MySQL.");
        IO.println("3. Выполнить сложение чисел, результат сохранить в MySQL.");
        IO.println("4. Выполнить вычитание чисел, результат сохранить в MySQL.");
        IO.println("5. Выполнить умножение чисел, результат сохранить в MySQL.");
        IO.println("6. Выполнить деление чисел, результат сохранить в MySQL.");
        IO.println("7. Вычислить остаток от деления, результат сохранить в MySQL.");
        IO.println("8. Вычислить модуль числа, результат сохранить в MySQL.");
        IO.println("9. Выполнить возведение числа в степень, результат сохранить в MySQL.");
        IO.println("10. Работа с байтовым типом данных, результат сохранить в MySQL.");
        IO.println("11. Экспортировать данные из MySQL в Excel и вывести на экран.");
    }

    @Override
    public void runCommandWithConnection(String command, Connection connection)
        throws RuntimeException
    {
        switch (command) {
            case "1" -> showTables(connection);
            case "2" -> createTable(connection, "varchar(255)");
            case "3" -> performAddition(connection);
            case "4" -> performSubtraction(connection);
            case "5" -> performMultiplication(connection);
            case "6" -> performDivision(connection);
            case "7" -> performModulo(connection);
            case "8" -> performAbsoluteValue(connection);
            case "9" -> performExponentiation(connection);
            case "10" -> performByteOperations(connection);
            case "11" -> saveToExcel(connection);
            default -> IO.println("Неверный номер команды. Попробуйте снова.");
        }
    }

    private void performAddition(Connection connection) throws RuntimeException {
        performBinaryOperation(connection, "сложения", "первое слагаемое", "второе слагаемое", 
            (a, b) -> a + b, "+");
    }

    private void performSubtraction(Connection connection) throws RuntimeException {
        performBinaryOperation(connection, "вычитания", "уменьшаемое", "вычитаемое", 
            (a, b) -> a - b, "-");
    }

    private void performMultiplication(Connection connection) throws RuntimeException {
        performBinaryOperation(connection, "умножения", "первый множитель", "второй множитель", 
            (a, b) -> a * b, "*");
    }

    @FunctionalInterface
    private interface DoubleOperation {
        double apply(double a, double b);
    }

    private void performBinaryOperation(Connection connection, String operationName, 
            String firstPrompt, String secondPrompt, DoubleOperation op, String symbol) {
        try {
            double first = Double.parseDouble(IO.readln("\nВведите " + firstPrompt + ": "));
            double second = Double.parseDouble(IO.readln("Введите " + secondPrompt + ": "));
            double result = op.apply(first, second);

            IO.println("\nРезультат " + operationName + ": " + first + " " + symbol + " " + second + " = " + result);
            finishQuery(connection, Double.toString(result), first + " " + symbol + " " + second + " = " + result);
        } catch (NumberFormatException e) {
            IO.println("Ошибка: введено некорректное число.");
        }
    }

    private void performDivision(Connection connection) throws RuntimeException {
        try {
            Double dividend = Double.parseDouble(IO.readln("\nВведите делимое: "));
            Double divisor = Double.parseDouble(IO.readln("Введите делитель: "));
            
            if (divisor == 0) {
                IO.println("Ошибка: деление на ноль невозможно.");
                return;
            }
            
            Double result = dividend / divisor;

            IO.println("\nРезультат деления: " + dividend + " / " + divisor + " = " + result);

            finishQuery(connection, result.toString(), dividend + " / " + divisor + " = " + result);
        } catch (NumberFormatException e) {
            IO.println("Ошибка: введено некорректное число.");
        }
    }

    private void performModulo(Connection connection) throws RuntimeException {
        try {
            Integer number = Integer.parseInt(IO.readln("\nВведите число: "));
            Integer modulus = Integer.parseInt(IO.readln("Введите модуль: "));
            
            if (modulus == 0) {
                IO.println("Ошибка: модуль не может быть равен нулю.");
                return;
            }
            
            Integer result = number % modulus;

            IO.println("\nОстаток от деления: " + number + " % " + modulus + " = " + result);

            finishQuery(connection, result.toString(), number + " % " + modulus + " = " + result);
        } catch (NumberFormatException e) {
            IO.println("Ошибка: введено некорректное число.");
        }
    }

    private void performAbsoluteValue(Connection connection) throws RuntimeException {
        try {
            double number = Double.parseDouble(IO.readln("\nВведите число: "));
            double result = Math.abs(number);

            IO.println("\nМодуль числа: |" + number + "| = " + result);

            finishQuery(connection, Double.toString(result), "|" + number + "| = " + result);
        } catch (NumberFormatException e) {
            IO.println("Ошибка: введено некорректное число.");
        }
    }

    private void performExponentiation(Connection connection) throws RuntimeException {
        try {
            double base = Double.parseDouble(IO.readln("\nВведите основание: "));
            double exponent = Double.parseDouble(IO.readln("Введите показатель степени: "));
            double result = Math.pow(base, exponent);

            IO.println("\nРезультат возведения в степень: " + base + " ^ " + exponent + " = " + result);

            finishQuery(connection, Double.toString(result), base + " ^ " + exponent + " = " + result);
        } catch (NumberFormatException e) {
            IO.println("Ошибка: введено некорректное число.");
        }
    }

    private void performByteOperations(Connection connection) throws RuntimeException {
        try {
            IO.println("\nРабота с байтовым типом данных:");
            byte firstByte = Byte.parseByte(IO.readln("Введите первое байтовое значение (-128 до 127): "));
            byte secondByte = Byte.parseByte(IO.readln("Введите второе байтовое значение (-128 до 127): "));

            if (secondByte == 0) {
                IO.println("Ошибка: второе значение не может быть равно нулю для операций деления и остатка.");
                return;
            }

            byte sum = (byte) (firstByte + secondByte);
            byte difference = (byte) (firstByte - secondByte);
            byte product = (byte) (firstByte * secondByte);
            byte quotient = (byte) (firstByte / secondByte);
            byte remainder = (byte) (firstByte % secondByte);

            IO.println("\nРезультаты операций с байтами:");
            IO.println("Сложение: " + firstByte + " + " + secondByte + " = " + sum);
            IO.println("Вычитание: " + firstByte + " - " + secondByte + " = " + difference);
            IO.println("Умножение: " + firstByte + " * " + secondByte + " = " + product);
            IO.println("Деление: " + firstByte + " / " + secondByte + " = " + quotient);
            IO.println("Остаток от деления: " + firstByte + " % " + secondByte + " = " + remainder);

            finishQuery(connection, "сумма=" + sum, firstByte + " + " + secondByte + " = " + sum);
            finishQuery(connection, "разность=" + difference, firstByte + " - " + secondByte + " = " + difference);
            finishQuery(connection, "произведение=" + product, firstByte + " * " + secondByte + " = " + product);
            finishQuery(connection, "частное=" + quotient, firstByte + " / " + secondByte + " = " + quotient);
            finishQuery(connection, "остаток=" + remainder, firstByte + " % " + secondByte + " = " + remainder);
        } catch (NumberFormatException e) {
            IO.println("Ошибка: введено некорректное байтовое значение. Допустимый диапазон: -128 до 127.");
        }
    }
}

