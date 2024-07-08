package main.java.ru.clevertec.check;

import java.math.BigDecimal;

public record Product(Integer id, String description, BigDecimal price, Integer quantity,
                      Boolean wholeSaleProduct) {}
