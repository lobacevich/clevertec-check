package main.java.ru.clevertec.check;

import java.math.BigDecimal;

public class ProductFactory {

    private static final ProductFactory INSTANCE = new ProductFactory();

    private ProductFactory() {
    }

    public static ProductFactory getINSTANCE() {
        return INSTANCE;
    }

    public Product createProduct(String[] data) {
        BigDecimal price = new BigDecimal(data[2].replace(',', '.'));
        return new Product(Integer.parseInt(data[0]),
                data[1], price, Integer.parseInt(data[3]),
                data[4].equals("+"));
    }
}
