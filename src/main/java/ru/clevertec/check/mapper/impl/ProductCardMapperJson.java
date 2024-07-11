package ru.clevertec.check.mapper.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.check.entity.Product;
import ru.clevertec.check.mapper.ProductCardMapper;

public class ProductCardMapperJson implements ProductCardMapper {

    private static final ProductCardMapper INSTANCE = new ProductCardMapperJson();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private ProductCardMapperJson() {
    }

    public static ProductCardMapper getINSTANCE() {
        return INSTANCE;
    }

    public Product toProduct(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Product.class);
    }

    public String toJson(Product product) throws JsonProcessingException {
        return objectMapper.writeValueAsString(product);
    }
}
