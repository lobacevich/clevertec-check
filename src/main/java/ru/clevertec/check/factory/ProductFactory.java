package ru.clevertec.check.factory;

import ru.clevertec.check.entity.Product;
import ru.clevertec.check.exception.InternalServerErrorException;

import java.math.BigDecimal;
import java.sql.ResultSet;

public class ProductFactory {

    private static final ProductFactory INSTANCE = new ProductFactory();

    private ProductFactory() {
    }

    public static ProductFactory getINSTANCE() {
        return INSTANCE;
    }

    public Product createProduct(ResultSet rs) {
        try {
            return new Product(rs.getLong("id"),
                    rs.getString("description"), BigDecimal.valueOf(rs.getDouble("price")),
                    rs.getInt("quantity_in_stock"), rs.getBoolean("wholesale_product"));
        } catch (Exception e) {
            throw new InternalServerErrorException("Can't read card result set");
        }
    }
}
