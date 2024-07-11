package ru.clevertec.check.service;

import ru.clevertec.check.entity.DiscountCard;
import ru.clevertec.check.exception.BadRequestException;

public interface DiscountCardService {
    DiscountCard getById(Long id) throws BadRequestException;

    void saveEntity(DiscountCard discountCard);

    void updateEntity(DiscountCard discountCard);
}
