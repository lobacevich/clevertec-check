package ru.clevertec.check.service.impl;

import ru.clevertec.check.csv.CsvWriter;
import ru.clevertec.check.dao.DiscountCardDao;
import ru.clevertec.check.dao.ProductDao;
import ru.clevertec.check.dao.impl.DiscountCardDaoImpl;
import ru.clevertec.check.dao.impl.ProductDaoImpl;
import ru.clevertec.check.entity.CheckData;
import ru.clevertec.check.entity.DiscountCard;
import ru.clevertec.check.entity.Order;
import ru.clevertec.check.entity.Product;
import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.InternalServerErrorException;
import ru.clevertec.check.exception.NotEnoughMoneyException;
import ru.clevertec.check.parser.ArgumentParser;
import ru.clevertec.check.service.ApplicationService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

public class ApplicationServiceImpl implements ApplicationService {

    private static final ApplicationService INSTANCE = new ApplicationServiceImpl();
    private static final Integer OTHER_CARD_DISCOUNT = 2;
    private static final Integer WHOLE_SALE_DISCOUNT = 10;
    private static final Integer WHOLE_SALE_AMOUNT = 5;
    private static final String RESULT_PATH = "./result.csv";
    private ProductDao productDao = ProductDaoImpl.getINSTANCE();
    private DiscountCardDao cardDao = DiscountCardDaoImpl.getINSTANCE();
    private ArgumentParser parser = ArgumentParser.getINSTANCE();
    private CsvWriter writer = CsvWriter.getInstance();
    private CheckData parsedArgs;
    private DiscountCard discountCard;
    private BigDecimal sumToPay;
    private String saveToFile;

    private ApplicationServiceImpl() {
    }

    public static ApplicationService getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void parseArgs(String[] args) throws BadRequestException {
        parsedArgs = parser.parseArguments(args);
    }

    @Override
    public void generateCheck(StringBuilder builder) throws BadRequestException, NotEnoughMoneyException {
        addDateTime(builder);
        setDiscountCard();
        for (Order order : parsedArgs.getOrders()) {
            fillOrder(order);
        }
        addProductsToCheck(builder);
        addDiscountCardToCheck(builder);
        addTotalSumToCheck(builder);
        isEnoughMoney();
    }

    @Override
    public void printCheck(String check) {
        try {
            writer.writeDataToCsv(check, saveToFile != null ?
                    saveToFile : RESULT_PATH);
            System.out.println("\n" + check);
        } catch (BadRequestException e) {
            System.out.println("Service: can't write to file");
            try {
                writer.writeDataToCsv(e.getMessage(), RESULT_PATH);
            } catch (BadRequestException ex) {
                throw new InternalServerErrorException("Write to file error");
            }
        }
    }

    @Override
    public void setSaveToFile(String[] args) throws BadRequestException {
        Optional<String> optSaveToFile = Arrays.stream(args)
                .filter(x -> x.startsWith("saveToFile="))
                .findFirst();
        if (optSaveToFile.isPresent()) {
            saveToFile = optSaveToFile.get().split("=")[1];
        } else {
            throw new BadRequestException();
        }
    }

    private void setDiscountCard() throws BadRequestException {
        Integer discountCardNumber = parsedArgs.getDiscountCardNumber();
        if (discountCardNumber != null) {
            Optional<DiscountCard> optDiscountCard = cardDao.getCardByCardNumber(discountCardNumber);
            if (optDiscountCard.isPresent()) {
                discountCard = optDiscountCard.get();
            } else {
                discountCard = new DiscountCard(null,
                        discountCardNumber, OTHER_CARD_DISCOUNT);
                cardDao.saveCard(discountCard);
            }
        }
    }

    private void addDateTime(StringBuilder builder) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy;HH.mm.ss");
        builder.append("Date;Time\n")
                .append(now.format(formatter))
                .append("\n");
    }

    private void fillOrder(Order order) throws BadRequestException {
        Product product;
        Optional<Product> optProduct = productDao.getProductById(order.getId());
        if (optProduct.isPresent()) {
            product = optProduct.get();
        } else {
            System.out.println("Service: product with id " + order.getId() + " not found");
            throw new BadRequestException();
        }
        if (order.getQuantity() > product.quantity()) {
            System.out.println("Service: not enough goods " + product.description() +
                    ", on order " + order.getQuantity() + ", in stock " + product.quantity());
            throw new BadRequestException();
        }
        order.setDescription(product.description());
        order.setPrice(product.price());
        order.setDiscount(order.getQuantity() >= WHOLE_SALE_AMOUNT && product.wholeSaleProduct() ?
                WHOLE_SALE_DISCOUNT : discountCard != null ? discountCard.discountAmount() : 0);
    }

    private void addProductsToCheck(StringBuilder builder) {
        builder.append("\nQTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL\n");
        for (Order order : parsedArgs.getOrders()) {
            BigDecimal total = order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity()))
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal discount = total.multiply(BigDecimal.valueOf(order.getDiscount()))
                    .divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
            builder.append(order.getQuantity())
                    .append(";")
                    .append(order.getDescription())
                    .append(";")
                    .append(order.getPrice())
                    .append("$;")
                    .append(discount)
                    .append("$;")
                    .append(total)
                    .append("$\n");
        }
    }

    private void addDiscountCardToCheck(StringBuilder builder) {
        if (discountCard != null) {
            builder.append("\nDISCOUNT CARD;DISCOUNT PERCENTAGE\n")
                    .append(discountCard.number())
                    .append(";")
                    .append(discountCard.discountAmount())
                    .append("%\n");
        }
    }

    private void addTotalSumToCheck(StringBuilder builder) {
        BigDecimal total = parsedArgs.getOrders().stream()
                .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalDiscount = parsedArgs.getOrders().stream()
                .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity()))
                        .multiply(BigDecimal.valueOf(x.getDiscount()))
                        .divide(BigDecimal.valueOf(100))
                        .setScale(2, RoundingMode.HALF_UP))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        sumToPay = total.subtract(totalDiscount);
        builder.append("\nTOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n")
                .append(total)
                .append("$;")
                .append(totalDiscount)
                .append("$;")
                .append(sumToPay)
                .append("$\n");
    }

    private void isEnoughMoney() throws NotEnoughMoneyException {
        if (sumToPay.compareTo(parsedArgs.getBalance()) > 0) {
            System.out.println("to pay - " + sumToPay + ", your balance is " + parsedArgs.getBalance());
            throw new NotEnoughMoneyException();
        }
    }
}
