package main.java.ru.clevertec.check;

import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private static final ProductDao INSTANCE = new ProductDaoImpl();
    private static final String PRODUCTS_PATH = "./src/main/resources/products.csv";
    private static final ProductFactory factory = ProductFactory.getINSTANCE();
    private final List<Product> products = new ArrayList<>();
    private final CsvWriter writer = CsvWriter.getInstance();

    private ProductDaoImpl() {
    }

    public static ProductDao getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void loadProducts() throws InternalServerErrorException {
        for (String line : writer.loadListDataFromCsv(PRODUCTS_PATH)) {
            products.add(factory.createProduct(line.split(";")));
        }
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }
}
