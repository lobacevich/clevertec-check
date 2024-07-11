package ru.clevertec.check.dao.impl;

import ru.clevertec.check.factory.DiscountCardFactory;
import ru.clevertec.check.dao.DiscountCardDao;
import ru.clevertec.check.db.Connect;
import ru.clevertec.check.entity.DiscountCard;
import ru.clevertec.check.exception.InternalServerErrorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DiscountCardDaoImpl extends AbstractDao<DiscountCard> implements DiscountCardDao {

    private static final DiscountCardDao INSTANCE = new DiscountCardDaoImpl();
    private static final String CREATE = "INSERT INTO public.discount_card(number, amount) " +
            "VALUES(?, ?)";
    private static final String UPDATE = "UPGRADE public.discount_card SET number=?, amount=? " +
            "WHERE id=?";
    private static final String DELETE = "DELETE FROM public.discount_card WHERE id=?";
    private static final String GET_BY_NUMBER = "SELECT * FROM public.discount_card WHERE number=?";
    private static final String GET_BY_ID = "SELECT * FROM public.discount_card WHERE id=?";
    private final Connection connection = Connect.getConnection();
    private final DiscountCardFactory factory = DiscountCardFactory.getINSTANCE();

    private DiscountCardDaoImpl()  {
    }

    public static DiscountCardDao getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void saveEntity(DiscountCard discountCard) {
        try(PreparedStatement ps = connection.prepareStatement(CREATE)) {
            ps.setInt(1, discountCard.number());
            ps.setInt(2, discountCard.discountAmount());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can not create discount card");
        }
    }

    @Override
    public void updateEntity(DiscountCard discountCard) {
        try(PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setInt(1, discountCard.number());
            ps.setInt(2, discountCard.discountAmount());
            ps.setLong(3, discountCard.id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can not create update card");
        }
    }

    @Override
    public void deleteEntity(Long id) {
        try(PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can not delete product");
        }
    }

    @Override
    public Optional<DiscountCard> getEntityById(Long id) {
        try (PreparedStatement ps = connection.prepareStatement(GET_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(factory.createDiscountCard(rs));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new InternalServerErrorException("Can not get discount card  by id");
        }
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
