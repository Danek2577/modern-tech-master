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
        IO.println("1. Вывести все таблицы из MySQL.");
        IO.println("2. Создать таблицу в MySQL.");
        IO.println("3. Сложение чисел, результат сохранить в MySQL с последующим выводом в консоль.");
        IO.println("4. Вычитание чисел, результат сохранить в MySQL с последующим выводом в консоль.");
        IO.println("5. Умножение чисел, результат сохранить в MySQL с последующим выводом в консоль.");
        IO.println("6. Деление чисел, результат сохранить в MySQL с последующим выводом в консоль.");
        IO.println("7. Деление чисел по модулю (остаток), результат сохранить в MySQL с последующим выводом в консоль.");
        IO.println("8. Возведение числа в модуль, результат сохранить в MySQL с последующим выводом в консоль.");
        IO.println("9. Возведение числа в степень, результат сохранить в MySQL с последующим выводом в консоль.");
        IO.println("10. Сохранить все данные (вышеполученные результаты) из MySQL в Excel и вывести на экран.");
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
            case "8" -> performModule(connection);
            case "9" -> performExponentiation(connection);
            case "10" -> saveToExcel(connection);
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

    private void performModule(Connection connection) throws RuntimeException {
        try {
            double number = Double.parseDouble(IO.readln("\nВведите число: "));
            double result = Math.abs(number);

            IO.println("\nВозведение числа в модуль: |" + number + "| = " + result);

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
}

