package ru.clevertec.check.dao;

import ru.clevertec.check.entity.DiscountCard;
import ru.clevertec.check.exception.BadRequestException;

import java.util.Optional;

public interface DiscountCardDao {

    void saveCard(DiscountCard card);

    Optional<DiscountCard> getCardByCardNumber(Integer cardNumber) throws BadRequestException;
}
