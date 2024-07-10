package ru.clevertec.check.dao.impl;

import ru.clevertec.check.factory.DiscountCardFactory;
import ru.clevertec.check.dao.DiscountCardDao;
import ru.clevertec.check.db.Connect;
import ru.clevertec.check.entity.DiscountCard;
import ru.clevertec.check.exception.InternalServerErrorException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DiscountCardDaoImpl implements DiscountCardDao {

    private static final DiscountCardDao INSTANCE = new DiscountCardDaoImpl();
    private static final String GET_BY_NUMBER = "SELECT * FROM public.discount_card WHERE number=?";
    private final DiscountCardFactory factory = DiscountCardFactory.getINSTANCE();

    private DiscountCardDaoImpl() {
    }

    public static DiscountCardDao getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void saveCard(DiscountCard card) {
    }

    @Override
    public Optional<DiscountCard> getCardByCardNumber(Integer cardNumber) {
        try (PreparedStatement ps = Connect.getConnection().prepareStatement(GET_BY_NUMBER)) {
            ps.setLong(1, cardNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(factory.createDiscountCard(rs));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can't create card prepare statement");
        }
    }
}
