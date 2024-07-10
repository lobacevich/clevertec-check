package main.java.ru.clevertec.check;

import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private static final ProductDao INSTANCE = new ProductDaoImpl();
    private final ProductFactory factory = ProductFactory.getINSTANCE();
    private final List<Product> products = new ArrayList<>();
    private final CsvWriter writer = CsvWriter.getInstance();

    private ProductDaoImpl() {
    }

    public static ProductDao getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void loadProducts(String pathToFile) throws BadRequestException {
        for (String line : writer.loadListDataFromCsv(pathToFile)) {
            products.add(factory.createProduct(line.split(";")));
        }
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }
}
