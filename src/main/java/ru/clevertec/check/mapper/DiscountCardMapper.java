package ru.clevertec.check.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.clevertec.check.entity.DiscountCard;

public interface DiscountCardMapper {

    DiscountCard toDiscontCard(String json) throws JsonProcessingException;

    String toJson(DiscountCard discountCard) throws JsonProcessingException;
}
