package ru.clevertec.check.service.impl;

import ru.clevertec.check.dao.DiscountCardDao;
import ru.clevertec.check.dao.impl.DiscountCardDaoImpl;
import ru.clevertec.check.entity.DiscountCard;
import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.service.DiscountCardService;

import java.util.Optional;

public class DiscountCardServiceImpl implements DiscountCardService {

    private static final DiscountCardService INSTANCE = new DiscountCardServiceImpl();
    private final DiscountCardDao dao = DiscountCardDaoImpl.getINSTANCE();

    private DiscountCardServiceImpl() {
    }

    public static DiscountCardService getInstance() {
        return INSTANCE;
    }

    @Override
    public DiscountCard getById(Long id) throws BadRequestException {
        Optional<DiscountCard> optDiscountCard = dao.getEntityById(id);
        if (optDiscountCard.isPresent()) {
            return optDiscountCard.get();
        } else {
            throw new BadRequestException();
        }
    }

    @Override
    public void saveEntity(DiscountCard discountCard) {
        dao.saveEntity(discountCard);
    }

    @Override
    public void updateEntity(DiscountCard discountCard) {
        dao.updateEntity(discountCard);
    }
}
