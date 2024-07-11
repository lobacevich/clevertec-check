package ru.clevertec.check.controller;

import ru.clevertec.check.service.CheckService;
import ru.clevertec.check.service.impl.CheckServiceImpl;
import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.NotEnoughMoneyException;
import ru.clevertec.check.validator.Validator;

public class ApplicationController {

    private static final ApplicationController INSTANCE = new ApplicationController();
    private CheckService service = CheckServiceImpl.getINSTANCE();
    private Validator validator = Validator.getINSTANCE();

    private ApplicationController() {
    }

    public static ApplicationController getINSTANCE() {
        return INSTANCE;
    }

    public void generateCheck(String[] args) {
        StringBuilder builder = new StringBuilder();
        try {
            service.setSaveToFile(args);
            validator.validateArgs(args);
            service.parseArgs(args);
            service.generateCheck(builder);
            service.printCheck(builder.toString());
        } catch (BadRequestException | NotEnoughMoneyException e) {
            service.printCheck(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}
