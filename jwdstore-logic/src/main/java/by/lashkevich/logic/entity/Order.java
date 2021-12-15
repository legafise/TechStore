package by.lashkevich.logic.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The type Order.
 * @author Roman Lashkevich
 */
public class Order implements Entity {
    private long id;
    private OrderStatus status;
    private String address;
    private BigDecimal price;
    private LocalDate date;
    private User customer;
    private Map<Good, Short> goods;

    /**
     * Instantiates a new Order.
     */
    public Order() {
        goods = new HashMap<>();
    }

    /**
     * Instantiates a new Order.
     *
     * @param id       the id
     * @param status   the status
     * @param address  the address
     * @param goods    the goods
     * @param price    the price
     * @param date     the date
     * @param customer the customer
     */
    public Order(long id, OrderStatus status, String address, Map<Good, Short> goods,
                 BigDecimal price, LocalDate date, User customer) {
        this.id = id;
        this.status = status;
        this.address = address;
        this.goods = goods;
        this.price = price;
        this.date = date;
        this.customer = customer;
    }

    /**
     * Instantiates a new Order.
     *
     * @param status   the status
     * @param address  the address
     * @param goods    the goods
     * @param price    the price
     * @param date     the date
     * @param customer the customer
     */
    public Order(OrderStatus status, String address, Map<Good, Short> goods, BigDecimal price,
                 LocalDate date, User customer) {
        this.status = status;
        this.address = address;
        this.goods = goods;
        this.price = price;
        this.date = date;
        this.customer = customer;
    }

    /**
     * Instantiates a new Order.
     *
     * @param id       the id
     * @param status   the status
     * @param address  the address
     * @param price    the price
     * @param date     the date
     * @param customer the customer
     */
    public Order(long id, OrderStatus status, String address, BigDecimal price, LocalDate date, User customer) {
        this.id = id;
        this.status = status;
        this.address = address;
        this.price = price;
        this.date = date;
        this.customer = customer;
        goods = new HashMap<>();
    }

    /**
     * Instantiates a new Order.
     *
     * @param status   the status
     * @param address  the address
     * @param price    the price
     * @param date     the date
     * @param customer the customer
     */
    public Order(OrderStatus status, String address, BigDecimal price, LocalDate date, User customer) {
        this.status = status;
        this.address = address;
        this.price = price;
        this.date = date;
        this.customer = customer;
        goods = new HashMap<>();
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets goods.
     *
     * @return the goods
     */
    public Map<Good, Short> getGoods() {
        return goods;
    }

    /**
     * Sets goods.
     *
     * @param goods the goods
     */
    public void setGoods(Map<Good, Short> goods) {
        this.goods = goods;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets customer.
     *
     * @return the customer
     */
    public User getCustomer() {
        return customer;
    }

    /**
     * Sets customer.
     *
     * @param customer the customer
     */
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
