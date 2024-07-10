package main.java.ru.clevertec.check;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArgumentParser {

    private static final ArgumentParser INSTANCE = new ArgumentParser();
    private final List<Order> orders = new ArrayList<>();

    private ArgumentParser() {
    }

    public static ArgumentParser getINSTANCE() {
        return INSTANCE;
    }

    public CheckData parseArguments(String[] args) throws BadRequestException {
        String cardNumber = null;
        BigDecimal balance = null;
        String pathToFile = null;
        for (String arg : args) {
            if (arg.startsWith("discountCard=")) {
                cardNumber = arg.split("=")[1];
            } else if (arg.startsWith("balanceDebitCard=")) {
                balance = new BigDecimal(arg.split("=")[1]);
            } else if (arg.startsWith("pathToFile=")) {
                pathToFile = arg.split("=")[1];
            } else if (arg.startsWith("saveToFile=")) {
            } else if (arg.contains("-")) {
                addOrCreateOrder(arg);
            } else {
                System.out.println("Parser: invalid argument " + arg);
                throw new BadRequestException();
            }
        }
        if (orders.isEmpty()) {
            System.out.println("Parser: in the parameter set must be at least one product.");
            throw new BadRequestException();
        }
        if (balance == null) {
            System.out.println("Parser: in the parameter set must be a balance of debit card.");
            throw new BadRequestException();
        }
        if (pathToFile == null) {
            System.out.println("Parser: in the parameter set must be a path to file.");
            throw new BadRequestException();
        }
        return new CheckData.Builder()
                .orders(orders)
                .discountCardNumber(cardNumber)
                .balance(balance)
                .pathToFile(pathToFile)
                .build();
    }

    private void addOrCreateOrder(String arg) throws BadRequestException {
        try {
            Integer id = Integer.parseInt(arg.split("-")[0]);
            Integer quantity = Integer.parseInt(arg.split("-")[1]);
            Optional<Order> optOrder = getOrder(id);
            if (optOrder.isEmpty()) {
                Order order = new Order(id, quantity);
                orders.add(order);
            } else {
                Order order = optOrder.get();
                order.setQuantity(order.getQuantity() + quantity);
            }
        } catch (NumberFormatException e) {
            System.out.println("Incorrect number format of argument " + arg +
                    ". Refactor validator");
            throw new BadRequestException();
        }
    }

    private Optional<Order> getOrder(Integer id) {
        return orders.stream()
                .filter(x -> x.getId() == id)
                .findFirst();
    }
}
