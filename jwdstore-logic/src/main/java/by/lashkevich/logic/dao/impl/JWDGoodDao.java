package by.lashkevich.logic.dao.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.GoodDao;
import by.lashkevich.logic.dao.mapper.DaoMapper;
import by.lashkevich.logic.dao.pool.ConnectionPool;
import by.lashkevich.logic.entity.Good;
import by.lashkevich.logic.entity.GoodType;

import java.sql.*;
import java.util.*;

/**
 * The type Jwd good dao.
 * @author Roman Lashkevich
 * @see GoodDao
 */
public class JWDGoodDao implements GoodDao {
    private static final String FIND_ALL_GOODS_SQL = "SELECT goods.id AS good_id, goods.name AS good_name," +
            " goods.price AS good_price, goods.description, goods.picture," +
            " goods_types.name AS good_type_name, goods_types.id AS good_type_id, reviews.id AS review_id, reviews.rate," +
            " reviews.content, users.id AS review_user_id, users.name AS review_user_name, users.surname" +
            " AS review_user_surname,users.login AS review_user_login, users.password AS review_user_password, users.email AS review_user_email," +
            " users.profile_picture AS review_user_profile_picture, users.birth_date AS review_user_birth_date,users.balance AS" +
            " review_user_balance, users.role AS review_user_role FROM goods LEFT JOIN goods_types ON goods.type_id = goods_types.id" +
            " LEFT JOIN goods_reviews ON goods.id = goods_reviews.good_id LEFT JOIN reviews ON" +
            " goods_reviews.review_id = reviews.id LEFT JOIN users ON users.id = reviews.user_id ORDER BY goods.id";
    private static final String FIND_GOOD_BY_ID_SQL = "SELECT goods.id AS good_id, goods.name AS good_name," +
            " goods.price AS good_price, goods.description, goods.picture," +
            " goods_types.name AS good_type_name, goods_types.id AS good_type_id, reviews.id AS review_id, reviews.rate," +
            " reviews.content, users.id AS review_user_id, users.name AS review_user_name, users.surname" +
            " AS review_user_surname,users.login AS review_user_login, users.password AS review_user_password, users.email AS review_user_email," +
            " users.profile_picture AS review_user_profile_picture, users.birth_date AS review_user_birth_date,users.balance AS" +
            " review_user_balance, users.role AS review_user_role FROM goods LEFT JOIN goods_types ON goods.type_id = goods_types.id" +
            " LEFT JOIN goods_reviews ON goods.id = goods_reviews.good_id LEFT JOIN reviews ON" +
            " goods_reviews.review_id = reviews.id LEFT JOIN users ON users.id = reviews.user_id WHERE goods.id = ?";
    private static final String REMOVE_GOOD_BY_ID_SQL = "DELETE FROM goods WHERE id = ?";
    private static final String FIND_TYPE_BY_ID_SQL = "SELECT goods_types.id AS good_type_id, goods_types.name AS" +
            " good_type_name FROM goods_types WHERE goods_types.id = ?";
    private static final String CREATE_GOOD_SQL = "INSERT INTO goods (name, price, type_id, description, picture)" +
            " VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_GOOD_SQL = "UPDATE goods SET name = ?, price = ?, type_id = ?, description = ?," +
            " picture = ? WHERE id = ?";
    private static final String FIND_ALL_TYPES_SQL = "SELECT goods_types.name AS good_type_name, goods_types.id AS good_type_id" +
            " FROM goods_types";
    private final DaoMapper daoMapper;

    /**
     * Instantiates a new Jwd good dao.
     */
    public JWDGoodDao() {
        daoMapper = new DaoMapper();
    }

    @Override
    public List<Good> findAll()  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_GOODS_SQL);
            List<Good> goods = new ArrayList<>();

            while (!resultSet.isAfterLast()) {
                goods.add(daoMapper.mapGood(resultSet));
            }

            return goods;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Good> findById(Long id)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_GOOD_BY_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Good good = daoMapper.mapGood(resultSet);

            return good.getId() != 0 ? Optional.of(good) : Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean add(Good good)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_GOOD_SQL)) {
            fillGoodData(good, statement);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean removeById(Long id)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_GOOD_BY_ID_SQL)) {
            statement.setLong(1, id);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<GoodType> findAllTypes() {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_TYPES_SQL);
            List<GoodType> types = new ArrayList<>();

            while (resultSet.next()) {
                types.add(daoMapper.mapGoodType(resultSet));
            }

            return types;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Good good)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_GOOD_SQL)) {
            statement.setLong(6, good.getId());
            fillGoodData(good, statement);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<GoodType> findTypeById(int typeId)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_TYPE_BY_ID_SQL)) {
            statement.setInt(1, typeId);
            ResultSet resultSet = statement.executeQuery();
            GoodType goodType = new GoodType();

            while (resultSet.next()) {
                goodType = daoMapper.mapGoodType(resultSet);
            }

            return goodType.getName() == null ? Optional.empty() : Optional.of(goodType);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void fillGoodData(Good good, PreparedStatement statement) throws SQLException {
        statement.setString(1, good.getName());
        statement.setBigDecimal(2, good.getPrice());
        statement.setInt(3, good.getType().getId());
        statement.setString(4, good.getDescription());
        statement.setString(5, good.getImgName());
    }
}