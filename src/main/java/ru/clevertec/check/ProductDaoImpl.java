package main.java.ru.clevertec.check;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private static final ProductDao INSTANCE = new ProductDaoImpl();
    private static final String PRODUCTS_PATH = "./src/main/resources/products.csv";
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
            String[] data = line.split(";");
            BigDecimal price = new BigDecimal(data[2].replace(',', '.'));
            Product product = new Product(Integer.parseInt(data[0]),
                    data[1], price, Integer.parseInt(data[3]),
                    data[4].equals("+"));
            products.add(product);
        }
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }
}
