package by.lashkevich.logic.service;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService extends Service {
    List<Order> findAllOrders() throws ServiceException;

    Order findById(String id) throws ServiceException;

    boolean addOrder(Order entity) throws ServiceException;

    boolean removeOrderById(String id) throws ServiceException;

    boolean updateOrder(Order entity) throws ServiceException;

    Optional<List<Order>> findOrdersByUserId(String userId) throws DaoException;
}
