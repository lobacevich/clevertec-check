package ru.clevertec.check.dao.impl;

import ru.clevertec.check.factory.ProductFactory;
import ru.clevertec.check.dao.ProductDao;
import ru.clevertec.check.db.Connect;
import ru.clevertec.check.entity.Product;
import ru.clevertec.check.exception.InternalServerErrorException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {

    private static final ProductDao INSTANCE = new ProductDaoImpl();
    private static final String GET_BY_ID = "SELECT * FROM public.product WHERE id=?";
    private final ProductFactory factory = ProductFactory.getINSTANCE();

    private ProductDaoImpl() {
    }

    public static ProductDao getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        try (PreparedStatement ps = Connect.getConnection().prepareStatement(GET_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(factory.createProduct(rs));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can't create card prepare statement");
        }
    }
}
