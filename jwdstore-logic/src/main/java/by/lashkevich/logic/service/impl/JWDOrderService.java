package by.lashkevich.logic.service.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.DaoFactory;
import by.lashkevich.logic.dao.OrderDao;
import by.lashkevich.logic.dao.UserDao;
import by.lashkevich.logic.dao.transaction.Transaction;
import by.lashkevich.logic.dao.transaction.TransactionManager;
import by.lashkevich.logic.entity.Order;
import by.lashkevich.logic.entity.User;
import by.lashkevich.logic.service.OrderService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.validator.OrderValidator;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class JWDOrderService implements OrderService {
    private static final String NONEXISTENT_ORDER_ID_MESSAGE = "Nonexistent order id was received";
    private static final String INVALID_ORDER_MESSAGE = "Invalid order was received";
    private static final String USER_DOES_NOT_HAVE_ORDERS_MESSAGE = "User does not have orders";
    private static final BigDecimal MIN_REMAINS = new BigDecimal("0.0");
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final Predicate<Order> orderValidator;
    private final TransactionManager transactionManager;

    public JWDOrderService() {
        orderDao = (OrderDao) DaoFactory.ORDER_DAO.getDao();
        userDao = (UserDao) DaoFactory.USER_DAO.getDao();
        orderValidator = new OrderValidator();
        transactionManager = TransactionManager.getInstance();
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
        Transaction transaction = transactionManager.createTransaction();
        try {
            if (orderValidator.test(order) && orderDao.add(order)) {
                Long orderId = findCurrentOrderId(order);
                if (order.getGoods().entrySet().stream()
                        .allMatch(entry -> orderDao.connectOrderToGood(orderId,
                                entry.getKey().getId(), entry.getValue()))) {
                    transactionManager.commit(transaction);
                    return true;
                } else {
                    transaction.rollback();
                    return false;
                }
            }

            throw new ServiceException(INVALID_ORDER_MESSAGE);
        } catch (DaoException e) {
            transactionManager.rollback(transaction);
            throw new ServiceException(e);
        } finally {
            transactionManager.closeTransaction(transaction);
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
        Transaction transaction = transactionManager.createTransaction();
        try {
            Long orderId = Long.valueOf(id);
            if (orderDao.removeConnectionBetweenOrderAndGood(orderId) && orderDao.removeById(orderId)) {
                transactionManager.commit(transaction);
            }

            return false;
        } catch (DaoException | NumberFormatException e) {
            transactionManager.rollback(transaction);
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.closeTransaction(transaction);
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

    @Override
    public boolean placeOrder(Order order) {
        Transaction transaction = transactionManager.createTransaction();
        try {
            User customer = order.getCustomer();
            BigDecimal remains = customer.getBalance().subtract(order.getPrice());

            if (remains.compareTo(MIN_REMAINS) < 0) {
                transactionManager.rollback(transaction);
                return false;
            }

            customer.setBalance(remains);
            if (addOrder(order) && userDao.update(customer)) {
                transactionManager.commit(transaction);
                return true;
            }

            transactionManager.rollback(transaction);
            return false;
        } catch (DaoException | NumberFormatException e) {
            transactionManager.rollback(transaction);
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.closeTransaction(transaction);
        }
    }
}
