package main.java.ru.clevertec.check;

import java.util.List;

public interface CardDao {

    void loadCards() throws InternalServerErrorException;

    List<DiscountCard> getCards();

    void addCard(DiscountCard card);
}
