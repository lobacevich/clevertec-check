package main.java.ru.clevertec.check;

import java.math.BigDecimal;
import java.util.List;

public class CheckData {

    private final List<Order> orders;
    private final String discountCardNumber;
    private final BigDecimal balance;

    public static class Builder {

        private List<Order> orders;
        private String discountCardNumber;
        private BigDecimal balance;

        public Builder orders(List<Order> value) {
            orders = value;
            return this;
        }

        public Builder discountCardNumber(String value) {
            discountCardNumber = value;
            return this;
        }

        public Builder balance(BigDecimal value) {
            balance = value;
            return this;
        }

        public CheckData build() {
            return new CheckData(this);
        }
    }

    public CheckData(Builder builder) {
        orders = builder.orders;
        discountCardNumber = builder.discountCardNumber;
        balance = builder.balance;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public String getDiscountCardNumber() {
        return discountCardNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "CheckData{" +
                "orders=" + orders +
                ", discountCardNumber='" + discountCardNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}
