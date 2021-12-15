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

/**
 * The type Jwd order service.
 *
 * @author Roman Lashkevich
 * @see OrderService
 */
public class JWDOrderService implements OrderService {
    private static final String NONEXISTENT_ORDER_ID_MESSAGE = "Nonexistent order id was received";
    private static final String INVALID_ORDER_MESSAGE = "Invalid order was received";
    private static final String USER_DOES_NOT_HAVE_ORDERS_MESSAGE = "User does not have orders";
    private static final BigDecimal MIN_REMAINS = new BigDecimal("0.0");
    private final UserDao userDao;
    private final TransactionManager transactionManager;
    private OrderValidator orderValidator;
    private OrderDao orderDao;

    public JWDOrderService() {
        orderDao = (OrderDao) DaoFactory.ORDER_DAO.getDao();
        userDao = (UserDao) DaoFactory.USER_DAO.getDao();
        orderValidator = new OrderValidator();
        transactionManager = TransactionManager.getInstance();
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setOrderValidator(OrderValidator orderValidator) {
        this.orderValidator = orderValidator;
    }

    @Override
    public List<Order> findAllOrders() {
        return orderDao.findAll();
    }

    @Override
    public Order findById(String id) {
        Optional<Order> orderOptional = orderDao.findById(Long.parseLong(id));

        if (orderOptional.isPresent()) {
            return orderOptional.get();
        }

        throw new ServiceException(NONEXISTENT_ORDER_ID_MESSAGE);
    }

    @Override
    public boolean addOrder(Order order) {
        Transaction transaction = transactionManager.createTransaction();
        try {
            if (orderValidator.validate(order) && orderDao.add(order)) {
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
    public boolean removeOrderById(String id) {
        Transaction transaction = transactionManager.createTransaction();
        try {
            Long orderId = Long.valueOf(id);
            if (orderDao.removeConnectionBetweenOrderAndGood(orderId) && orderDao.removeById(orderId)) {
                transactionManager.commit(transaction);
            }

            return false;
        } finally {
            transactionManager.closeTransaction(transaction);
        }
    }

    @Override
    public boolean updateOrder(Order order) {
        if (orderValidator.validate(order) && order.getId() != 0) {
            return orderDao.update(order);
        }

        throw new ServiceException(INVALID_ORDER_MESSAGE);
    }

    @Override
    public Optional<List<Order>> findOrdersByUserId(String userId) {
        return orderDao.findOrdersByUserId(Long.parseLong(userId));
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
        } finally {
            transactionManager.closeTransaction(transaction);
        }
    }
}
