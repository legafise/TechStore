package by.lashkevich.logic.service.validator;

import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.entity.Order;
import by.lashkevich.logic.entity.User;

import java.util.Map;

/**
 * The type Order validator.
 * @author Roman Lashkevich
 */
public class OrderValidator {
    public boolean validate(Order order) {
        return order.getStatus() != null && validateAddress(order.getAddress())
                && order.getPrice() != null && order.getDate() != null
                && validateGoods(order.getGoods()) && validateCustomer(order.getCustomer());
    }

    private boolean validateAddress(String address) {
        return address != null && address.length() >= 7 && address.length() <= 80;
    }

    private boolean validateCustomer(User customer) {
        return customer != null && customer.getId() != 0;
    }

    private boolean validateGoods(Map<Good, Short> goods) {
        return goods != null && !goods.keySet().isEmpty()
                && goods.keySet().stream()
                .noneMatch(currentGood -> currentGood.getId() == 0)
                && goods.values().stream()
                .noneMatch(amount -> amount < 1);
    }
}
