package by.lashkevich.logic.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The type Basket.
 * @author Roman Lashkevich
 */
public class Basket implements Entity {
    private Map<Good, Integer> goods;
    private User owner;

    /**
     * Instantiates a new Basket.
     */
    public Basket() {
        goods = new HashMap<>();
    }

    /**
     * Instantiates a new Basket.
     *
     * @param goods the goods
     * @param owner the owner
     */
    public Basket(Map<Good, Integer> goods, User owner) {
        this.goods = goods;
        this.owner = owner;
    }

    /**
     * Gets goods.
     *
     * @return the goods
     */
    public Map<Good, Integer> getGoods() {
        return goods;
    }

    /**
     * Sets goods.
     *
     * @param goods the goods
     */
    public void setGoods(Map<Good, Integer> goods) {
        this.goods = goods;
    }

    /**
     * Gets owner.
     *
     * @return the owner
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Sets owner.
     *
     * @param owner the owner
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(goods, basket.goods) &&
                Objects.equals(owner, basket.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goods, owner);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "goods=" + goods +
                ", owner=" + owner +
                '}';
    }
}
