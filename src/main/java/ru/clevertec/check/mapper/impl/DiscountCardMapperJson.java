package ru.clevertec.check.mapper.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.check.entity.DiscountCard;
import ru.clevertec.check.mapper.DiscountCardMapper;

public class DiscountCardMapperJson implements DiscountCardMapper {

    private static final DiscountCardMapper INSTANCE = new DiscountCardMapperJson();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private DiscountCardMapperJson() {
    }

    public static DiscountCardMapper getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public DiscountCard toDiscontCard(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, DiscountCard.class);
    }

    @Override
    public String toJson(DiscountCard discountCard) throws JsonProcessingException {
        return objectMapper.writeValueAsString(discountCard);
    }
}
