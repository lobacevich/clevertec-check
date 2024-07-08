package main.java.ru.clevertec.check;

import java.util.List;

public interface ProductDao {

    void loadProducts() throws InternalServerErrorException;

    List<Product> getProducts();
}
