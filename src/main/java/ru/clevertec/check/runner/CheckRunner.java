package ru.clevertec.check.runner;

import ru.clevertec.check.controller.ApplicationController;

public class CheckRunner {

    private final static ApplicationController controller = ApplicationController.getINSTANCE();

    public static void main(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("datasource.url=")) {
                System.setProperty("datasource.url", arg.split("=")[1]);
            } else if (arg.startsWith("datasource.username=")) {
                System.setProperty("datasource.username", arg.split("=")[1]);
            } else if (arg.startsWith("datasource.password=")) {
                System.setProperty("datasource.password", arg.split("=")[1]);
            }
        }
        controller.generateCheck(args);
    }
}
