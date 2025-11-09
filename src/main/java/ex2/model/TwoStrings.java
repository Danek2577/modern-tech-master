package ex2.model;

import common.Model;
import static common.Model.IO;

import java.sql.Connection;
import java.util.Objects;

public class TwoStrings extends Model {
    private String firstString = null;
    private String secondString = null;
    private static final int MIN_STRING_LENGTH = 50;

    @Override
    public String getDescribeMessage() {
        return "Модель работы с двумя строками";
    }

    @Override
    public void showCommands() {
        IO.println("\nДоступные команды:");
        IO.println("1. Вывести список таблиц из MySQL.");
        IO.println("2. Создать новую таблицу в MySQL.");
        IO.println("3. Ввести две строки с клавиатуры (не менее 50 символов каждая), результат сохранить в MySQL.");
        IO.println("4. Вывести на экран две введенных ранее строки.");
        IO.println("5. Подсчитать и вывести размер длины каждой строки, результат сохранить в MySQL.");
        IO.println("6. Объединить две ранее введенные строки в одну, результат сохранить в MySQL.");
        IO.println("7. Сравнить две ранее введенные строки, результат сравнения вывести на экран и сохранить в MySQL.");
        IO.println("8. Экспортировать данные из MySQL в Excel и вывести на экран.");
    }

    @Override
    public void runCommandWithConnection(String command, Connection connection)
        throws RuntimeException
    {
        switch (command) {
            case "1" -> showTables(connection);
            case "2" -> createTable(connection, "TEXT");
            case "3" -> inputTwoStrings(connection);
            case "4" -> displayTwoStrings(connection);
            case "5" -> calculateStringLengths(connection);
            case "6" -> concatenateStrings(connection);
            case "7" -> compareStoredStrings(connection);
            case "8" -> saveToExcel(connection);
            default -> IO.println("Неверный номер команды. Попробуйте снова.");
        }
    }

    private void inputTwoStrings(Connection connection) throws RuntimeException {
        IO.println("\nВведите две строки (каждая не менее " + MIN_STRING_LENGTH + " символов):");
        
        firstString = readStringWithMinLength("первую", MIN_STRING_LENGTH);
        secondString = readStringWithMinLength("вторую", MIN_STRING_LENGTH);

        IO.println("\nСтроки успешно введены:");
        IO.println("Первая строка: " + firstString);
        IO.println("Вторая строка: " + secondString);

        finishQuery(connection, firstString, "Первая строка: " + firstString);
        finishQuery(connection, secondString, "Вторая строка: " + secondString);
    }

    private String readStringWithMinLength(String ordinal, int minLength) {
        String input = "";
        while (input.length() < minLength) {
            input = IO.readln("Введите " + ordinal + " строку (не менее " + minLength + " символов): ");
            if (input.length() < minLength) {
                IO.println("Ошибка: строка должна содержать не менее " + minLength + " символов. Текущая длина: " + input.length());
            }
        }
        return input;
    }

    private void displayTwoStrings(Connection connection) throws RuntimeException {
        if (!checkStringsAvailable()) {
            return;
        }

        IO.println("\nДве введенных ранее строки:");
        IO.println("Первая строка: " + firstString);
        IO.println("Вторая строка: " + secondString);
    }

    private void calculateStringLengths(Connection connection) throws RuntimeException {
        if (!checkStringsAvailable()) {
            return;
        }
        
        int firstLength = firstString.length();
        int secondLength = secondString.length();

        IO.println("\nРазмер длины каждой строки:");
        IO.println("Длина первой строки: " + firstLength);
        IO.println("Длина второй строки: " + secondLength);

        finishQuery(connection, Integer.toString(firstLength), "Длина первой строки: " + firstLength);
        finishQuery(connection, Integer.toString(secondLength), "Длина второй строки: " + secondLength);
    }

    private void concatenateStrings(Connection connection) throws RuntimeException {
        if (!checkStringsAvailable()) {
            return;
        }

        String concatenated = firstString + secondString;

        IO.println("\nРезультат объединения двух строк:");
        IO.println("Объединенная строка: " + concatenated);

        finishQuery(connection, concatenated, firstString + " + " + secondString + " = " + concatenated);
    }

    private void compareStoredStrings(Connection connection) throws RuntimeException {
        if (!checkStringsAvailable()) {
            return;
        }

        boolean areEqual = Objects.equals(firstString, secondString);
        String result = areEqual ? "строки равны" : "строки не равны";
        String comparisonMessage = "Результат сравнения: " + result;

        IO.println("\nРезультат сравнения двух строк:");
        IO.println(comparisonMessage);

        finishQuery(connection, result, comparisonMessage);
    }

    private boolean checkStringsAvailable() {
        if (firstString == null || secondString == null) {
            IO.println("Ошибка: необходимо сначала ввести две строки (команда 3).");
            return false;
        }
        return true;
    }
}

