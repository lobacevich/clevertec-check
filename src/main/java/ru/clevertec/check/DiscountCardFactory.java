package main.java.ru.clevertec.check;

public class DiscountCardFactory {

    private static final DiscountCardFactory INSTANCE = new DiscountCardFactory();

    private DiscountCardFactory() {
    }

    public static DiscountCardFactory getINSTANCE() {
        return INSTANCE;
    }

    public DiscountCard createDiscountCard(String[] data) {
        return new DiscountCard(Integer.parseInt(data[0]),
                data[1], Integer.parseInt(data[2]));
    }
}
