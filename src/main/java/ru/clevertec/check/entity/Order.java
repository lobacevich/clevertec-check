package ru.clevertec.check.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Order {

    private final Long id;
    private Integer quantity;
    private String description;
    private BigDecimal price;
    private Integer Discount;

    public Order(Long id, Integer quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return Discount;
    }

    public void setDiscount(Integer discount) {
        Discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(quantity, order.quantity) && Objects.equals(description, order.description) && Objects.equals(price, order.price) && Objects.equals(Discount, order.Discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, description, price, Discount);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", Discount=" + Discount +
                '}';
    }
}
