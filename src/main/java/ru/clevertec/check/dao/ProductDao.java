package ru.clevertec.check.dao;

import ru.clevertec.check.entity.Product;

import java.util.Optional;

public interface ProductDao {

    Optional<Product> getProductById(Long id);
}
