package ru.clevertec.check;

import ru.clevertec.check.entity.DiscountCard;
import ru.clevertec.check.entity.Order;
import ru.clevertec.check.entity.ParsedArgs;
import ru.clevertec.check.entity.Product;

import java.math.BigDecimal;
import java.util.Collections;

public class ApplicationTestData {

    private static final String[] args = new String[]{"1-4", "1-1", "discountCard=1111",
            "balanceDebitCard=100", "saveToFile=./result.csv",
            "datasource.url=jdbc:postgresql://localhost:5432/postgres",
            "datasource.username=postgres", "datasource.password=postgres"};
    private static final String[] invalidArgs = new String[]{"1-4", "1-1", "discountCard=1111",
            "balanceDebitCard=100", "saveToFile=./result.csv",
            "datasource.username=postgres", "datasource.password=postgres"};
    private static final Product product = new Product(1L, "Milk", BigDecimal.valueOf(1.07),
            10, true);
    private static final DiscountCard discountCard = new DiscountCard(1L, 1111,3);
    private static final Order order = new Order(1L, 5);
    private static final ParsedArgs parsedArgs = new ParsedArgs.Builder()
            .orders(Collections.singletonList(order))
            .discountCardNumber(1111)
            .balance(BigDecimal.valueOf(100))
            .datasourceUrl("jdbc:postgresql://localhost:5432/postgres")
            .datasourceUserName("postgres")
            .datasourcePassword("postgres")
            .build();
    private static final String checkString =
            """
            QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL
            5;Milk;1.07$;0.54$;5.35$
            
            DISCOUNT CARD;DISCOUNT PERCENTAGE
            1111;3%
            
            TOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT
            5.35$;0.54$;4.81$""";
    private static final String saveToFile = "./result.csv";
    private static final String argSaveToFile = "saveToFile=./result2.csv";

    public static Product getProduct() {
        return product;
    }

    public static DiscountCard getDiscountCard() {
        return discountCard;
    }

    public static String[] getArgs() {
        return args;
    }

    public static ParsedArgs getParsedArgs() {
        return parsedArgs;
    }

    public static String getCheckString() {
        return checkString;
    }

    public static String getSaveToFile() {
        return saveToFile;
    }

    public static String getArgSaveToFile() {
        return argSaveToFile;
    }

    public static String[] getInvalidArgs() {
        return invalidArgs;
    }
}
