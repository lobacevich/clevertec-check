package ru.clevertec.check.service.impl;

import ru.clevertec.check.service.ProductService;

public class ProductServiceImpl implements ProductService {

    private final static ProductService INSTANCE = new ProductServiceImpl();

    private ProductServiceImpl() {
    }

    public static ProductService getINSTANCE() {
        return INSTANCE;
    }


}
