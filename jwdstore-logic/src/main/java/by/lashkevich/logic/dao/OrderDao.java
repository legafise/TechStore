package by.lashkevich.logic.dao;

import by.lashkevich.logic.entity.Order;

import java.util.List;
import java.util.Optional;

/**
 * The interface Order dao.
 * @author Roman Lashkevich
 * @see BaseDao
 */
public interface OrderDao extends BaseDao<Long, Order> {
    /**
     * Find orders by user id optional.
     *
     * @param userId the user id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<List<Order>> findOrdersByUserId(Long userId) throws DaoException;

    /**
     * Connect order to good boolean.
     *
     * @param orderId  the order id
     * @param goodId   the good id
     * @param quantity the quantity
     * @return the boolean
     */
    boolean connectOrderToGood(Long orderId, Long goodId, int quantity);

    /**
     * Remove connection between order and good boolean.
     *
     * @param orderId the order id
     * @return the boolean
     */
    boolean removeConnectionBetweenOrderAndGood(Long orderId);
}
