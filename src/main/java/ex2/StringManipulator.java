package ex2;

import common.Control;
import ex2.model.TwoStrings;

public class StringManipulator {
    public static void main(String[] args) {
        try {
            Control<TwoStrings> control = new Control<>(new TwoStrings());
            control.connectToLocalDb();
            control.handleCommands();
        } catch (RuntimeException e) {
            System.err.println("Произошла ошибка при выполнении программы.");
            System.err.println("Сообщение об ошибке: " + e.getMessage());
        }
    }
}

