package main.java.ru.clevertec.check;

import java.util.List;

public interface ProductDao {

    void loadProducts(String pahToFile) throws BadRequestException;

    List<Product> getProducts();
}
