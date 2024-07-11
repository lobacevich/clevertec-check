package ru.clevertec.check.runner;

import ru.clevertec.check.controller.ApplicationController;

public class CheckRunner {

    private final static ApplicationController controller = ApplicationController.getINSTANCE();

    static {
        System.setProperty("datasource.url", "jdbc:postgresql://localhost:5432/postgres");
        System.setProperty("datasource.username", "postgres");
        System.setProperty("datasource.password", "postgres");
    }

    public static void main(String[] args) {
        controller.generateCheck(args);
    }
}
