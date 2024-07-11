package ru.clevertec.check.dao;

import ru.clevertec.check.entity.DiscountCard;
import ru.clevertec.check.exception.BadRequestException;

import java.util.Optional;

public interface DiscountCardDao extends GenericDao<DiscountCard> {

    Optional<DiscountCard> getCardByCardNumber(Integer cardNumber) throws BadRequestException;
}
