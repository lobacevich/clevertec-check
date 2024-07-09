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
            service.loadData();
            validator.validateArgs(args);
            service.parseArgs(args);
            service.generateCheck(builder);
            service.printCheck(builder.toString());
            System.out.println("\n" + builder);
        } catch (BadRequestException | NotEnoughMoneyException | InternalServerErrorException e) {
            service.printCheck(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
}
