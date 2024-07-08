package main.java.ru.clevertec.check;

public class CheckRunner {

    private final static ApplicationController controller = ApplicationController.getINSTANCE();

    public static void main(String[] args) {
        controller.generateCheck(args);
    }
}
