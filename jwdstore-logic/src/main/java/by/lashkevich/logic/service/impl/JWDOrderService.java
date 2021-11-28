package by.lashkevich.logic.service.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.DaoFactory;
import by.lashkevich.logic.dao.OrderDao;
import by.lashkevich.logic.dao.transaction.Transaction;
import by.lashkevich.logic.dao.transaction.TransactionFactory;
import by.lashkevich.logic.entity.Order;
import by.lashkevich.logic.service.OrderService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.validator.OrderValidator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class JWDOrderService implements OrderService {
    private static final String NONEXISTENT_ORDER_ID_MESSAGE = "Nonexistent order id was received";
    private static final String INVALID_ORDER_MESSAGE = "Invalid order was received";
    private static final String USER_DOES_NOT_HAVE_ORDERS_MESSAGE = "User does not have orders";
    private final OrderDao orderDao;
    private final Predicate<Order> orderValidator;

    public JWDOrderService() {
        orderDao = (OrderDao) DaoFactory.ORDER_DAO.getDao();
        orderValidator = new OrderValidator();
    }

    @Override
    public List<Order> findAllOrders() throws ServiceException {
        try {
            return orderDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Order findById(String id) throws ServiceException {
        try {
            Optional<Order> orderOptional = orderDao.findById(Long.parseLong(id));

            if (orderOptional.isPresent()) {
                return orderOptional.get();
            }

            throw new ServiceException(NONEXISTENT_ORDER_ID_MESSAGE);
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public boolean addOrder(Order order) throws ServiceException {
        Transaction transaction = TransactionFactory.getInstance().createTransaction();
        try {
            if (orderValidator.test(order) && orderDao.add(order)) {
                Long orderId = findCurrentOrderId(order);
                if (order.getGoods().entrySet().stream()
                        .allMatch(entry -> orderDao.connectOrderToGood(orderId,
                                entry.getKey().getId(), entry.getValue()))) {
                    transaction.commit();
                    return true;
                } else {
                    transaction.rollback();
                    return false;
                }
            }

            throw new ServiceException(INVALID_ORDER_MESSAGE);
        } catch (DaoException e) {
            transaction.rollback();
            throw new ServiceException(e);
        } finally {
            transaction.closeTransaction();
        }
    }

    private Long findCurrentOrderId(Order order) {
        Optional<List<Order>> orders = orderDao.findOrdersByUserId(order.getCustomer().getId());
        if (orders.isPresent()) {
            return Collections.max(orders.get().stream()
                    .map(Order::getId)
                    .collect(Collectors.toList()));
        }

        throw new ServiceException(USER_DOES_NOT_HAVE_ORDERS_MESSAGE);
    }

    @Override
    public boolean removeOrderById(String id) throws ServiceException {
        Transaction transaction = TransactionFactory.getInstance().createTransaction();
        try {
            Long orderId = Long.valueOf(id);
            if (orderDao.removeConnectionBetweenOrderAndGood(orderId) && orderDao.removeById(orderId)) {
                transaction.commit();
                return true;
            }

            return false;
        } catch (DaoException | NumberFormatException e) {
            transaction.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transaction.closeTransaction();
        }
    }

    @Override
    public boolean updateOrder(Order order) throws ServiceException {
        try {
            if (orderValidator.test(order) && order.getId() != 0) {
                return orderDao.update(order);
            }

            throw new ServiceException(INVALID_ORDER_MESSAGE);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Optional<List<Order>> findOrdersByUserId(String userId) throws DaoException {
        try {
            return orderDao.findOrdersByUserId(Long.parseLong(userId));
        } catch (DaoException | NumberFormatException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
