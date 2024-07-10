package ru.clevertec.check.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ParsedArgs {

    private final List<Order> orders;
    private final Integer discountCardNumber;
    private final BigDecimal balance;
    private final String datasourceUrl;
    private final String datasourceUserName;
    private final String datasourcePassword;

    public static class Builder {

        private List<Order> orders;
        private Integer discountCardNumber;
        private BigDecimal balance;
        private String datasourceUrl;
        private String datasourceUserName;
        private String datasourcePassword;

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

        public Builder datasourceUrl(String value) {
            datasourceUrl = value;
            return this;
        }

        public Builder datasourceUserName(String value) {
            datasourceUserName = value;
            return this;
        }

        public Builder datasourcePassword(String value) {
            datasourcePassword = value;
            return this;
        }

        public ParsedArgs build() {
            return new ParsedArgs(this);
        }
    }

    public ParsedArgs(Builder builder) {
        orders = builder.orders;
        discountCardNumber = builder.discountCardNumber;
        balance = builder.balance;
        datasourceUrl = builder.datasourceUrl;
        datasourceUserName = builder.datasourceUserName;
        datasourcePassword = builder.datasourcePassword;
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

    public String getDatasourceUrl() {
        return datasourceUrl;
    }

    public String getDatasourceUserName() {
        return datasourceUserName;
    }

    public String getDatasourcePassword() {
        return datasourcePassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParsedArgs that = (ParsedArgs) o;
        return Objects.equals(orders, that.orders) && Objects.equals(discountCardNumber, that.discountCardNumber) && Objects.equals(balance, that.balance) && Objects.equals(datasourceUrl, that.datasourceUrl) && Objects.equals(datasourceUserName, that.datasourceUserName) && Objects.equals(datasourcePassword, that.datasourcePassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orders, discountCardNumber, balance, datasourceUrl, datasourceUserName, datasourcePassword);
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
