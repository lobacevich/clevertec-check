package main.java.ru.clevertec.check;

public class ApplicationController {

    private static final ApplicationController INSTANCE = new ApplicationController();
    private final ApplicationService service = ApplicationServiceImpl.getINSTANCE();
    private final Validator validator = Validator.getINSTANCE();

    private ApplicationController() {
    }

    public static ApplicationController getINSTANCE() {
        return INSTANCE;
    }

    public void generateCheck(String[] args) {
        StringBuilder builder = new StringBuilder();
        try {
            service.setSaveToFile(validator.getSaveToFile(args));
            validator.validateArgs(args);
            service.parseArgs(args);
            service.loadData();
            service.generateCheck(builder);
            service.printCheck(builder.toString());
        } catch (BadRequestException | NotEnoughMoneyException e) {
            service.printCheck(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}
