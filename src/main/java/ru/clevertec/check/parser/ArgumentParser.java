package ru.clevertec.check.parser;

import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.entity.Order;
import ru.clevertec.check.entity.ParsedArgs;

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

    public ParsedArgs parseArguments(String[] args) throws BadRequestException {
        Integer cardNumber = null;
        BigDecimal balance = null;
        String datasourceUrl = null;
        String datasourceUserName = null;
        String datasourcePassword = null;
        for (String arg : args) {
            if (arg.startsWith("discountCard=")) {
                cardNumber = Integer.parseInt(arg.split("=")[1]);
            } else if (arg.startsWith("balanceDebitCard=")) {
                balance = new BigDecimal(arg.split("=")[1]);
            } else if (arg.startsWith("saveToFile=")) {

            } else if (arg.startsWith("datasource.url=")) {
                datasourceUrl = arg.split("=")[1];
            } else if (arg.startsWith("datasource.username=")) {
                datasourceUserName = arg.split("=")[1];
            } else if (arg.startsWith("datasource.password=")) {
                datasourcePassword = arg.split("=")[1];
            } else if (arg.contains("-")) {
                addOrCreateOrder(arg);
            } else {
                System.out.println("Parser: invalid argument " + arg);
                throw new BadRequestException();
            }
        }
        return new ParsedArgs.Builder()
                .orders(orders)
                .discountCardNumber(cardNumber)
                .balance(balance)
                .datasourceUrl(datasourceUrl)
                .datasourceUserName(datasourceUserName)
                .datasourcePassword(datasourcePassword)
                .build();
    }

    private void addOrCreateOrder(String arg) throws BadRequestException {
        try {
            Long id = Long.parseLong(arg.split("-")[0]);
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

    private Optional<Order> getOrder(Long id) {
        return orders.stream()
                .filter(x -> x.getId() == id)
                .findFirst();
    }
}
