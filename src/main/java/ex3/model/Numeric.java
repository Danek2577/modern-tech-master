package ex3.model;

import common.Model;
import static common.Model.IO;

import java.sql.Connection;

public class Numeric extends Model {
    @Override
    public String getDescribeMessage() {
        return "Модель валидации чисел";
    }

    @Override
    public void showCommands() {
        IO.println("\nДоступные команды:");
        IO.println("1. Вывести список таблиц из MySQL.");
        IO.println("2. Создать новую таблицу в MySQL.");
        IO.println("3. Проверить число на целостность и четность, результат сохранить в MySQL.");
        IO.println("4. Экспортировать данные из MySQL в Excel и вывести на экран.");
    }

    @Override
    public void runCommandWithConnection(String command, Connection connection)
        throws RuntimeException
    {
        switch (command) {
            case "1" -> showTables(connection);
            case "2" -> createTable(connection, "varchar(255)");
            case "3" -> validateNumber(connection);
            case "4" -> saveToExcel(connection);
            default -> IO.println("Неверный номер команды. Попробуйте снова.");
        }
    }

    private void validateNumber(Connection connection) throws RuntimeException {
        String input = IO.readln("\nВведите число для проверки: ");
        
        int number;
        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            IO.println("Ошибка: введено не целое число или некорректный формат.");
            finishQuery(connection, "ошибка: не целое число", "Попытка ввода: '" + input + "' - не целое число");
            return;
        }

        boolean isEven = number % 2 == 0;
        String parityResult = isEven ? "четное" : "нечетное";
        String parityDescription = "Число " + number + " является " + parityResult;

        IO.println("\n" + parityDescription);
        finishQuery(connection, parityResult, number + " -> " + parityResult + " (" + parityDescription + ")");
    }
}

