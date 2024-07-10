package main.java.ru.clevertec.check;

public class Validator {

    private static final String ID_PATTERN = "\\d+-\\d+";
    private static final String DISCOUNT_CARD_PATTERN = "discountCard=\\d{4}";
    private static final String BALANCE_PATTERN = "balanceDebitCard=-?\\d+(\\.\\d{2})?";
    private static final String PATH_TO_FILE_PATTERN = "pathToFile=\\./.*\\.csv";
    private static final String SAVE_TO_FILE_PATTERN = "saveToFile=\\./.*\\.csv";
    private static final Validator INSTANCE = new Validator();

    private Validator() {
    }

    public static Validator getINSTANCE() {
        return INSTANCE;
    }

    public void validateArgs(String[] args) throws BadRequestException {
        validateArg(args[0], ID_PATTERN);
        for (int i = 1; i < args.length - 1; i++) {
            if (args[i].contains("-") && !args[i].contains("=-")) {
                if (args[i - 1].contains("-") && !args[i - 1].contains("=-")) {
                    validateArg(args[i], ID_PATTERN);
                } else {
                    throwBadRequestException();
                }
            } else if (args[i].startsWith("discountCard=")) {
                if (args[i - 1].contains("-") && !args[i - 1].contains("=-")) {
                    validateArg(args[i], DISCOUNT_CARD_PATTERN);
                } else {
                    throwBadRequestException();
                }
            } else if (args[i].startsWith("balanceDebitCard=")) {
                if (args[i - 1].contains("-") && !args[i - 1].contains("=-") ||
                        args[i - 1].startsWith("discountCard=")) {
                    validateArg(args[i], BALANCE_PATTERN);
                } else {
                    throwBadRequestException();
                }
            } else if (args[i].startsWith("pathToFile=")) {
                if ((args[i - 1]).startsWith("balanceDebitCard=")) {
                    validateArg(args[i], PATH_TO_FILE_PATTERN);
                } else {
                    throwBadRequestException();
                }
            } else {
                System.out.println("Validator: invalid argument format " + args[i] +
                        " or invalid sequence of arguments");
                throw new BadRequestException();
            }
        }
    }

    public String getSaveToFile(String[] args) throws BadRequestException {
        validateArg(args[args.length - 1], SAVE_TO_FILE_PATTERN);
        return args[args.length - 1];
    }

    private void validateArg(String arg, String pattern) throws BadRequestException {
        if (!arg.matches(pattern)) {
            System.out.println("Validator: incorrect format of argument: " + arg);
            throw new BadRequestException();
        }
    }

    private void throwBadRequestException() throws BadRequestException {
        System.out.println("Validator: invalid sequence of arguments");
        throw new BadRequestException();
    }
}
