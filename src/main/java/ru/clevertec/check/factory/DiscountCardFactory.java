package ru.clevertec.check.factory;

import ru.clevertec.check.entity.DiscountCard;
import ru.clevertec.check.exception.InternalServerErrorException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountCardFactory {

    private static final DiscountCardFactory INSTANCE = new DiscountCardFactory();

    private DiscountCardFactory() {
    }

    public static DiscountCardFactory getINSTANCE() {
        return INSTANCE;
    }

    public DiscountCard createDiscountCard(ResultSet rs) {
        try {
            return new DiscountCard(rs.getLong("id"),
                    rs.getInt("number"), rs.getInt("amount"));
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can't read card result set");
        }
    }
}
