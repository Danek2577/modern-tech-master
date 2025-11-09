package ex3;

import common.Control;
import ex3.model.Numeric;

public class NumbersValidator {
    public static void main(String[] args) {
        try {
            Control<Numeric> control = new Control<>(new Numeric());
            control.connectToLocalDb();
            control.handleCommands();
        } catch (RuntimeException e) {
            System.err.println("Произошла ошибка при выполнении программы.");
            System.err.println("Сообщение об ошибке: " + e.getMessage());
        }
    }
}

