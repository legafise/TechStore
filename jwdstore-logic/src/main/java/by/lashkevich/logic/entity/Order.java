package by.lashkevich.logic.entity;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class Order {
    private long id;
    private String orderNumber;
    private OrderStatus status;
    private String address;
    private Map<Good, Integer> goods;
    private BigDecimal price;

    public Order(long id, String orderNumber, OrderStatus status,
                 String address, Map<Good, Integer> goods, BigDecimal price) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.status = status;
        this.address = address;
        this.goods = goods;
        this.price = price;
    }

    public Order() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<Good, Integer> getGoods() {
        return goods;
    }

    public void setGoods(Map<Good, Integer> goods) {
        this.goods = goods;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                Objects.equals(orderNumber, order.orderNumber) &&
                status == order.status &&
                Objects.equals(address, order.address) &&
                Objects.equals(goods, order.goods) &&
                Objects.equals(price, order.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber, status, address, goods, price);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", status=" + status +
                ", address='" + address + '\'' +
                ", goods=" + goods +
                ", price=" + price +
                '}';
    }
}
