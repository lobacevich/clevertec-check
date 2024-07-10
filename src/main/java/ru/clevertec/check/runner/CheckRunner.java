package ru.clevertec.check.runner;

import ru.clevertec.check.controller.ApplicationController;

public class CheckRunner {

    private final static ApplicationController controller = ApplicationController.getINSTANCE();

    public static void main(String[] args) {
        controller.generateCheck(args);
    }
}
