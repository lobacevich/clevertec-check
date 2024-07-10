package main.java.ru.clevertec.check;

import java.util.List;

public interface CardDao {

    void loadCards() throws BadRequestException;

    List<DiscountCard> getCards();

    void addCard(DiscountCard card);
}
