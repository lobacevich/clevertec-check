package ru.clevertec.check.entity;

import java.math.BigDecimal;

public record Product(Long id, String description, BigDecimal price, Integer quantity,
                      Boolean wholeSaleProduct) {}
