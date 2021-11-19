package by.lashkevich.logic.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Order implements Entity {
    private long id;
    private OrderStatus status;
    private String address;
    private BigDecimal price;
    private LocalDate date;
    private User customer;
    private Map<Good, Integer> goods;

    public Order() {
        goods = new HashMap<>();
    }

    public Order(long id, OrderStatus status, String address, Map<Good, Integer> goods,
                 BigDecimal price, LocalDate date, User customer) {
        this.id = id;
        this.status = status;
        this.address = address;
        this.goods = goods;
        this.price = price;
        this.date = date;
        this.customer = customer;
    }

    public Order(OrderStatus status, String address, Map<Good, Integer> goods, BigDecimal price,
                 LocalDate date, User customer) {
        this.status = status;
        this.address = address;
        this.goods = goods;
        this.price = price;
        this.date = date;
        this.customer = customer;
    }

    public Order(long id, OrderStatus status, String address, BigDecimal price, LocalDate date, User customer) {
        this.id = id;
        this.status = status;
        this.address = address;
        this.price = price;
        this.date = date;
        this.customer = customer;
        goods = new HashMap<>();
    }

    public Order(OrderStatus status, String address, BigDecimal price, LocalDate date, User customer) {
        this.status = status;
        this.address = address;
        this.price = price;
        this.date = date;
        this.customer = customer;
        goods = new HashMap<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                status == order.status &&
                Objects.equals(address, order.address) &&
                Objects.equals(goods, order.goods) &&
                Objects.equals(price, order.price) &&
                Objects.equals(date, order.date) &&
                Objects.equals(customer, order.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, address, goods, price, date, customer);
    }

    @Override
    public String toString() {
        return "\nOrder{" +
                "id=" + id +
                ", status=" + status +
                ", address='" + address + '\'' +
                ", goods=" + goods +
                ", price=" + price +
                ", date=" + date +
                ", customer=" + customer +
                '}' + "\n";
    }
}
