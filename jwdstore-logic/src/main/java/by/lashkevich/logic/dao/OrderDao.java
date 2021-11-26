package by.lashkevich.logic.dao;

import by.lashkevich.logic.entity.Order;

import java.util.List;

public interface OrderDao extends BaseDao<Long, Order> {
    List<Order> findOrdersByUserId(Long userId) throws DaoException;
    boolean connectOrderToGood(Long orderId, Long goodId, int quantity);
    boolean removeConnectionBetweenOrderAndGood(Long orderId);
}
