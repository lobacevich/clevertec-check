package main.java.ru.clevertec.check;

public class Validator {

    private static final String ID_PATTERN = "\\d+-\\d+";
    private static final String DISCOUNT_CARD_PATTERN = "discountCard=\\d{4}";
    private static final String BALANCE_PATTERN = "balanceDebitCard=-?\\d+(\\.\\d+)?";
    private Validator() {
    }

    public static void validateArgs(String[] args) throws BadRequestException {
        validateId(args[0]);
        for (int i = 1; i < args.length - 1; i++) {
            if (args[i].contains("-")) {
                if (args[i - 1].contains("-")) {
                    validateId(args[i]);
                } else {
                    System.out.println("invalid sequence of arguments");
                    throw new BadRequestException();
                }
            } else if(args[i].startsWith("discountCard=")) {
                if (args[i - 1].contains("-")) {
                    validateDiscountCard(args[i]);
                } else {
                    System.out.println("invalid sequence of arguments");
                    throw new BadRequestException();
                }
            } else {
                System.out.println("invalid argument format " + args[i] +
                        " or invalid sequence of arguments");
                throw new BadRequestException();
            }
        }
        validateBalance(args[args.length - 1]);
    }

    private static void validateId(String arg) throws BadRequestException {
        if (!arg.matches(ID_PATTERN)) {
            System.out.println("Incorrect format of first argument " + arg);
            throw new BadRequestException();
        }
    }

    private static void validateDiscountCard(String arg) throws BadRequestException {
        if (!arg.matches(DISCOUNT_CARD_PATTERN)) {
            System.out.println("Invalid discount card format");
            throw new BadRequestException();
        }
    }

    private static void validateBalance(String arg) throws BadRequestException {
        if (!arg.matches(BALANCE_PATTERN)) {
            System.out.println("Incorrect format of debit card balance " + arg);
            throw new BadRequestException();
        }
    }
}
