package main.java.ru.clevertec.check;

import java.util.ArrayList;
import java.util.List;

public class CardDaoImpl implements CardDao {

    private static final CardDao INSTANCE = new CardDaoImpl();
    private static final String CARDS_PATH = "./src/main/resources/discountCards.csv";
    private final List<DiscountCard> cards = new ArrayList<>();
    private final CsvWriter writer = CsvWriter.getInstance();
    private final DiscountCardFactory factory = DiscountCardFactory.getINSTANCE();

    private CardDaoImpl() {
    }

    public static CardDao getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void loadCards() throws InternalServerErrorException {
        for (String line : writer.loadListDataFromCsv(CARDS_PATH)) {
            cards.add(factory.createDiscountCard(line.split(";")));
        }
    }

    @Override
    public List<DiscountCard> getCards() {
        return cards;
    }

    @Override
    public void addCard(DiscountCard card) {
        cards.add(card);
    }
}
