package ru.clevertec.check.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class CheckData {

    private final List<Order> orders;
    private final Integer discountCardNumber;
    private final BigDecimal balance;

    public static class Builder {

        private List<Order> orders;
        private Integer discountCardNumber;
        private BigDecimal balance;

        public Builder orders(List<Order> value) {
            orders = value;
            return this;
        }

        public Builder discountCardNumber(Integer value) {
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

    public Integer getDiscountCardNumber() {
        return discountCardNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckData that = (CheckData) o;
        return Objects.equals(orders, that.orders) && Objects.equals(discountCardNumber, that.discountCardNumber) && Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders, discountCardNumber, balance);
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
