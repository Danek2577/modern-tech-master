package ex1;

import common.Control;
import ex1.model.Arithmetic;

public class Calculator {
    public static void main(String[] args) {
        try {
            Control<Arithmetic> control = new Control<>(new Arithmetic());
            control.connectToLocalDb();
            control.handleCommands();
        } catch (RuntimeException e) {
            System.err.println("Произошла ошибка при выполнении программы.");
            System.err.println("Сообщение об ошибке: " + e.getMessage());
        }
    }
}

