package main.java.ru.clevertec.check;

import java.util.ArrayList;
import java.util.List;

public class CardDaoImpl implements CardDao {

    private static final CardDao INSTANCE = new CardDaoImpl();
    private static final String CARDS_PATH = "./src/main/resources/discountCards.csv";
    private final List<DiscountCard> cards = new ArrayList<>();
    private final CsvWriter writer = CsvWriter.getInstance();

    private CardDaoImpl() {
    }

    public static CardDao getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void loadCards() throws InternalServerErrorException {
        for (String line : writer.loadListDataFromCsv(CARDS_PATH)) {
            String[] data = line.split(";");
            DiscountCard card = new DiscountCard(Integer.parseInt(data[0]),
                    data[1], Integer.parseInt(data[2]));
            cards.add(card);
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
