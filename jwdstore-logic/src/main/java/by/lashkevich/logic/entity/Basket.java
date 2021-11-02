package by.lashkevich.logic.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Basket {
    private Map<Good, Integer> goods;

    public Basket(Map<Good, Integer> goods) {
        this.goods = goods;
    }

    public Basket() {
        goods = new HashMap<>();
    }

    public Map<Good, Integer> getGoods() {
        return goods;
    }

    public void setGoods(Map<Good, Integer> goods) {
        this.goods = goods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(goods, basket.goods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goods);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "goods=" + goods +
                '}';
    }
}
