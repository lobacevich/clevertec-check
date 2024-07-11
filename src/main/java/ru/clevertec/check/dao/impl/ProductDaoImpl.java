package ru.clevertec.check.dao.impl;

import ru.clevertec.check.factory.ProductFactory;
import ru.clevertec.check.dao.ProductDao;
import ru.clevertec.check.db.Connect;
import ru.clevertec.check.entity.Product;
import ru.clevertec.check.exception.InternalServerErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProductDaoImpl extends AbstractDao<Product> implements ProductDao {

    private static final ProductDao INSTANCE = new ProductDaoImpl();
    private static final String CREATE = "INSERT INTO public.product(description, price, quantity_in_stock, " +
            "wholesale_product) VALUES(?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE public.product SET description=?, price=?, quantity_in_stock=?, " +
            "wholesale_product=? WHERE id=?";
    private static final String DELETE = "DELETE FROM public.product WHERE id=?";
    private static final String GET_BY_ID = "SELECT * FROM public.product WHERE id=?";
    private final Connection connection = Connect.getConnection();
    private final ProductFactory factory = ProductFactory.getINSTANCE();

    private ProductDaoImpl() {
    }

    public static ProductDao getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void saveEntity(Product product) {
        try(PreparedStatement ps = connection.prepareStatement(CREATE)) {
            ps.setString(1, product.description());
            ps.setDouble(2, product.price().doubleValue());
            ps.setInt(3, product.quantity());
            ps.setBoolean(4, product.wholeSaleProduct());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can not create product");
        }
    }

    @Override
    public void updateEntity(Product product) {
        try(PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, product.description());
            ps.setDouble(2, product.price().doubleValue());
            ps.setInt(3, product.quantity());
            ps.setBoolean(4, product.wholeSaleProduct());
            ps.setLong(5, product.id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can not update product");
        }
    }

    @Override
    public void deleteEntity(Long id) {
        try(PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can not delete product");
        }
    }

    @Override
    public Optional<Product> getEntityById(Long id) {
        try (PreparedStatement ps = connection.prepareStatement(GET_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(factory.createProduct(rs));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can not get product by id");
        }
    }
}
