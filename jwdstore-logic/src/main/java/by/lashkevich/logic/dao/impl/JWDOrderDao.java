package by.lashkevich.logic.dao.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.OrderDao;
import by.lashkevich.logic.dao.mapper.DaoMapper;
import by.lashkevich.logic.dao.pool.ConnectionPool;
import by.lashkevich.logic.entity.Order;
import by.lashkevich.logic.entity.OrderStatusException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JWDOrderDao implements OrderDao {
    private static final String FIND_ALL_ORDERS_SQL = "SELECT orders.id AS order_id, orders.status AS order_status, orders.price AS order_price, orders.address AS" +
            " order_address, orders.date AS order_date, users_from_orders.id AS user_id, users_from_orders.name AS user_name, users_from_orders.surname AS" +
            " user_surname, users_from_orders.login AS user_login, users_from_orders.password AS user_password, users_from_orders.email AS user_email," +
            " users_from_orders.profile_picture AS user_profile_picture, users_from_orders.birth_date AS user_birth_date, users_from_orders.balance AS" +
            " user_balance, users_from_orders.role AS user_role, goods.id AS good_id, goods.name AS good_name, goods.price AS" +
            " good_price, goods.description AS good_description,goods.picture AS good_picture, goods_types.name AS" +
            " good_type_name, ordered_goods.quantity AS good_quatity, reviews.id AS review_id, reviews.rate AS" +
            " review_rate, reviews.content AS review_content, users_from_reviews.id AS review_user_id, users_from_reviews.name AS review_user_name, users_from_reviews.surname AS" +
            " review_user_surname, users_from_reviews.login AS review_user_login, users_from_reviews.password AS review_user_password, users_from_reviews.email AS review_user_email," +
            " users_from_reviews.profile_picture AS review_user_profile_picture, users_from_reviews.birth_date AS review_user_birth_date, users_from_reviews.balance AS" +
            " review_user_balance, users_from_reviews.role AS review_user_role, ordered_goods.quantity AS good_quantity FROM orders LEFT JOIN users AS users_from_orders ON" +
            " users_from_orders.id = orders.user_id LEFT JOIN ordered_goods ON ordered_goods.order_id = orders.id LEFT JOIN goods" +
            " ON ordered_goods.good_id = goods.id LEFT JOIN goods_types ON goods.type_id = goods_types.id LEFT JOIN" +
            " goods_reviews ON goods_reviews.good_id = goods.id LEFT JOIN reviews ON goods_reviews.review_id = reviews.id" +
            " LEFT JOIN users AS users_from_reviews ON users_from_reviews.id = reviews.user_id" +
            " ORDER BY orders.id, goods.id";
    private static final String FIND_ORDER_BY_ID_SQL = "SELECT orders.id AS order_id, orders.status AS order_status, orders.price AS order_price, orders.address AS" +
            " order_address, orders.date AS order_date, users_from_orders.id AS user_id, users_from_orders.name AS user_name, users_from_orders.surname AS" +
            " user_surname, users_from_orders.login AS user_login, users_from_orders.password AS user_password, users_from_orders.email AS user_email," +
            " users_from_orders.profile_picture AS user_profile_picture, users_from_orders.birth_date AS user_birth_date, users_from_orders.balance AS" +
            " user_balance, users_from_orders.role AS user_role, goods.id AS good_id, goods.name AS good_name, goods.price AS" +
            " good_price, goods.description AS good_description,goods.picture AS good_picture, goods_types.name AS" +
            " good_type_name, ordered_goods.quantity AS good_quatity, reviews.id AS review_id, reviews.rate AS" +
            " review_rate, reviews.content AS review_content, users_from_reviews.id AS review_user_id, users_from_reviews.name AS review_user_name, users_from_reviews.surname AS" +
            " review_user_surname, users_from_reviews.login AS review_user_login, users_from_reviews.password AS review_user_password, users_from_reviews.email AS review_user_email," +
            " users_from_reviews.profile_picture AS review_user_profile_picture, users_from_reviews.birth_date AS review_user_birth_date, users_from_reviews.balance AS" +
            " review_user_balance, users_from_reviews.role AS review_user_role, ordered_goods.quantity AS good_quantity FROM orders LEFT JOIN users AS users_from_orders ON" +
            " users_from_orders.id = orders.user_id LEFT JOIN ordered_goods ON ordered_goods.order_id = orders.id LEFT JOIN goods" +
            " ON ordered_goods.good_id = goods.id LEFT JOIN goods_types ON goods.type_id = goods_types.id LEFT JOIN" +
            " goods_reviews ON goods_reviews.good_id = goods.id LEFT JOIN reviews ON goods_reviews.review_id = reviews.id" +
            " LEFT JOIN users AS users_from_reviews ON users_from_reviews.id = reviews.user_id WHERE orders.id = ?" +
            " ORDER BY orders.id, goods.id";
    private static final String FIND_ORDERS_BY_USER_ID_SQL = "SELECT orders.id AS order_id, orders.status AS order_status, orders.price AS order_price, orders.address AS" +
            " order_address, orders.date AS order_date, users_from_orders.id AS user_id, users_from_orders.name AS user_name, users_from_orders.surname AS" +
            " user_surname, users_from_orders.login AS user_login, users_from_orders.password AS user_password, users_from_orders.email AS user_email," +
            " users_from_orders.profile_picture AS user_profile_picture, users_from_orders.birth_date AS user_birth_date, users_from_orders.balance AS" +
            " user_balance, users_from_orders.role AS user_role, goods.id AS good_id, goods.name AS good_name, goods.price AS" +
            " good_price, goods.description AS good_description,goods.picture AS good_picture, goods_types.name AS" +
            " good_type_name, ordered_goods.quantity AS good_quatity, reviews.id AS review_id, reviews.rate AS" +
            " review_rate, reviews.content AS review_content, users_from_reviews.id AS review_user_id, users_from_reviews.name AS review_user_name, users_from_reviews.surname AS" +
            " review_user_surname, users_from_reviews.login AS review_user_login, users_from_reviews.password AS review_user_password, users_from_reviews.email AS review_user_email," +
            " users_from_reviews.profile_picture AS review_user_profile_picture, users_from_reviews.birth_date AS review_user_birth_date, users_from_reviews.balance AS" +
            " review_user_balance, users_from_reviews.role AS review_user_role, ordered_goods.quantity AS good_quantity FROM orders RIGHT JOIN users AS users_from_orders ON" +
            " users_from_orders.id = orders.user_id LEFT JOIN ordered_goods ON ordered_goods.order_id = orders.id LEFT JOIN goods" +
            " ON ordered_goods.good_id = goods.id LEFT JOIN goods_types ON goods.type_id = goods_types.id LEFT JOIN" +
            " goods_reviews ON goods_reviews.good_id = goods.id LEFT JOIN reviews ON goods_reviews.review_id = reviews.id" +
            " LEFT JOIN users AS users_from_reviews ON users_from_reviews.id = reviews.user_id WHERE users_from_orders.id = ?" +
            " ORDER BY orders.id, goods.id";
    private static final String REMOVE_ORDER_BY_ID_SQL = "DELETE FROM orders WHERE id = ?";
    private static final String CREATE_ORDER_SQL = "INSERT INTO orders (status, price, user_id, address, date) VALUES" +
            " (?, ?, ?, ?, ?)";
    private static final String UPDATE_ORDER_SQL = "UPDATE orders SET status = ?, price = ?, user_id = ?," +
            " address = ?, date = ? WHERE id = ?";
    private static final String CONNECT_ORDER_TO_GOOD = "INSERT INTO ordered_goods (order_id, good_id, quantity) VALUES" +
            " (?, ?, ?)";
    private static final String REMOVE_CONNECTION_BETWEEN_ORDER_AND_GOODS = "DELETE FROM ordered_goods WHERE order_id = ?";
    private static final String UNKNOWN_USER_MESSAGE = "Unknown user id";
    private final DaoMapper daoMapper;

    public JWDOrderDao() {
        daoMapper = new DaoMapper();
    }

    @Override
    public List<Order> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_ORDERS_SQL);
            List<Order> orders = new ArrayList<>();

            while (!resultSet.isAfterLast()) {
                orders.add(daoMapper.mapOrder(resultSet));
            }

            return orders;
        } catch (SQLException | OrderStatusException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Order> findById(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ORDER_BY_ID_SQL);) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Order order = daoMapper.mapOrder(resultSet);

            return order.getId() != 0 ? Optional.of(order) : Optional.empty();
        } catch (SQLException | OrderStatusException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean add(Order order) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ORDER_SQL)) {
            fillOrderData(order, statement);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean removeById(Long id) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_ORDER_BY_ID_SQL)) {
            statement.setLong(1, id);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findOrdersByUserId(Long userId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ORDERS_BY_USER_ID_SQL)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<Order> orders = new ArrayList<>();

            while (!resultSet.isAfterLast()) {
                Order order = daoMapper.mapOrder(resultSet);
                if (order.getCustomer() == null) {
                    throw new DaoException(UNKNOWN_USER_MESSAGE);
                }

                orders.add(order);
            }

            return orders;
        } catch (SQLException | OrderStatusException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Order order) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_SQL)) {
            statement.setLong(6, order.getId());
            fillOrderData(order, statement);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean connectOrderToGood(Long orderId, Long goodId, int quantity) {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(CONNECT_ORDER_TO_GOOD)) {
            statement.setLong(1, orderId);
            statement.setLong(2, goodId);
            statement.setInt(3, quantity);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean removeConnectionBetweenOrderAndGood(Long orderId) {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_CONNECTION_BETWEEN_ORDER_AND_GOODS)) {
            statement.setLong(1, orderId);

            return statement.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void fillOrderData(Order order, PreparedStatement statement) throws SQLException {
        statement.setString(1, order.getStatus().getStatusContent());
        statement.setBigDecimal(2, order.getPrice());
        statement.setLong(3, order.getCustomer().getId());
        statement.setString(4, order.getAddress());
        statement.setDate(5, Date.valueOf(order.getDate()));
    }
}
