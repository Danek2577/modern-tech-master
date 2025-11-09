package ex4;

import common.Control;
import ex4.model.StringMethods;

public class StringExplorer {
    public static void main(String[] args) {
        try {
            Control<StringMethods> control = new Control<>(new StringMethods());
            control.connectToLocalDb();
            control.handleCommands();
        } catch (RuntimeException e) {
            System.err.println("Произошла ошибка при выполнении программы.");
            System.err.println("Сообщение об ошибке: " + e.getMessage());
        }
    }
}

