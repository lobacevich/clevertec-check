package main.java.ru.clevertec.check;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ApplicationServiceImpl implements ApplicationService {

    private static final ApplicationService INSTANCE = new ApplicationServiceImpl();
    private static final Integer OTHER_CARD_DISCOUNT = 2;
    private static final Integer WHOLE_SALE_DISCOUNT = 10;
    private static final Integer WHOLE_SALE_AMOUNT = 5;
    private static final String RESULT_PATH = "./result.csv";
    private final ProductDao productDao = ProductDaoImpl.getINSTANCE();
    private final CardDao cardDao = CardDaoImpl.getINSTANCE();
    private final ArgumentParser parser = ArgumentParser.getINSTANCE();
    private final CsvWriter writer = CsvWriter.getInstance();
    private DiscountCard discountCard;
    private BigDecimal sumToPay;

    private ApplicationServiceImpl() {
    }

    public static ApplicationService getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void loadData() throws InternalServerErrorException {
        productDao.loadProducts();
        System.out.println("Products are loaded successfully");
        cardDao.loadCards();
        System.out.println("Cards are loaded successfully");
    }

    @Override
    public void parseArgs(String[] args) throws BadRequestException {
        parser.parseArguments(args);
    }

    @Override
    public void generateCheck(StringBuilder builder) throws BadRequestException, NotEnoughMoneyException {
        addDateTime(builder);
        setDiscountCard();
        for (Order order : parser.getOrders()) {
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
            writer.writeDataToCsv(check, parser.getSaveToFile() != null ?
                    parser.getSaveToFile() : RESULT_PATH);
        } catch (InternalServerErrorException e) {
            System.out.println("Service: can't write to file from args");
            writer.writeDataToCsv(check, RESULT_PATH);
        }
    }

    private void setDiscountCard() {
        String discountCardNumber = parser.getCardNumber();
        if (discountCardNumber != null) {
            Optional<DiscountCard> optDiscountCard = cardDao.getCards()
                    .stream()
                    .filter(x -> x.number().equals(discountCardNumber))
                    .findFirst();
            if (optDiscountCard.isPresent()) {
                discountCard = optDiscountCard.get();
            } else {
                discountCard = new DiscountCard(cardDao.getCards().size() + 1,
                        discountCardNumber, OTHER_CARD_DISCOUNT);
                cardDao.addCard(discountCard);
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
        Optional<Product> optProduct = productDao.getProducts()
                .stream()
                .filter(x -> x.id() == order.getId())
                .findFirst();
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
        for (Order order : parser.getOrders()) {
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
        BigDecimal total = parser.getOrders().stream()
                .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalDiscount = parser.getOrders().stream()
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
        if (sumToPay.compareTo(parser.getBalance()) > 0) {
            System.out.println("to pay - " + sumToPay + ", your balance is " + parser.getBalance());
            throw new NotEnoughMoneyException();
        }
    }
}
