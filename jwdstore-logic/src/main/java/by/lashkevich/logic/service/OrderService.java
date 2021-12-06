package by.lashkevich.logic.service;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.entity.Order;

import java.util.List;
import java.util.Optional;

/**
 * The interface Order service.
 * @author Roman Lashkevich
 */
public interface OrderService extends Service {
    /**
     * Find all orders list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Order> findAllOrders() throws ServiceException;

    /**
     * Find by id order.
     *
     * @param id the id
     * @return the order
     * @throws ServiceException the service exception
     */
    Order findById(String id) throws ServiceException;

    /**
     * Add order boolean.
     *
     * @param entity the entity
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean addOrder(Order entity) throws ServiceException;

    /**
     * Remove order by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean removeOrderById(String id) throws ServiceException;

    /**
     * Update order boolean.
     *
     * @param entity the entity
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateOrder(Order entity) throws ServiceException;

    /**
     * Find orders by user id optional.
     *
     * @param userId the user id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<List<Order>> findOrdersByUserId(String userId) throws DaoException;

    /**
     * Place order boolean.
     *
     * @param order the order
     * @return the boolean
     */
    boolean placeOrder(Order order);
}
