package by.lashkevich.logic.dao.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.GoodDao;
import by.lashkevich.logic.dao.mapper.DaoMapper;
import by.lashkevich.logic.entity.Good;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JWDGoodDao implements GoodDao {
    private static final String UNKNOWN_TYPE_ERROR_MESSAGE = "Unknown good type(%s) was received";
    private static final String FIND_ALL_GOODS_SQL = "SELECT goods.id AS good_id, goods.name AS good_name," +
            " goods.price AS good_price, goods.description AS good_description, goods.picture AS good_picture," +
            " goods_types.name AS good_type_name, reviews.id AS review_id, reviews.rate AS review_rate," +
            " reviews.content AS review_content, users.id AS user_id, users.name AS user_name, users.surname" +
            " AS user_surname,users.login AS user_login, users.password AS user_password, users.email AS user_email," +
            " users.profile_picture AS user_profile_picture, users.birth_date AS user_birth_date,users.balance AS" +
            " user_balance, users.role AS user_role FROM goods LEFT JOIN goods_types ON goods.type_id = goods_types.id" +
            " LEFT JOIN goods_reviews ON goods.id = goods_reviews.good_id LEFT JOIN reviews ON" +
            " goods_reviews.review_id = reviews.id LEFT JOIN users ON users.id = reviews.user_id ORDER BY goods.id";
    private static final String FIND_GOOD_BY_ID_SQL = "SELECT goods.id AS good_id, goods.name AS good_name," +
            " goods.price AS good_price, goods.description AS good_description, goods.picture AS good_picture," +
            " goods_types.name AS good_type_name, reviews.id AS review_id, reviews.rate AS review_rate," +
            " reviews.content AS review_content, users.id AS user_id, users.name AS user_name, users.surname" +
            " AS user_surname,users.login AS user_login, users.password AS user_password, users.email AS user_email," +
            " users.profile_picture AS user_profile_picture, users.birth_date AS user_birth_date,users.balance AS" +
            " user_balance, users.role AS user_role FROM goods LEFT JOIN goods_types ON goods.type_id = goods_types.id" +
            " LEFT JOIN goods_reviews ON goods.id = goods_reviews.good_id LEFT JOIN reviews ON" +
            " goods_reviews.review_id = reviews.id LEFT JOIN users ON users.id = reviews.user_id WHERE goods.id = ?";
    private static final String REMOVE_GOOD_BY_ID_SQL = "DELETE FROM goods WHERE id = ?";
    private static final String FIND_TYPE_BY_NAME_SQL = "SELECT goods_types.id AS good_type_id" +
            " FROM goods_types WHERE name = ?";
    private static final String CREATE_GOOD_SQL = "INSERT INTO goods (name, price, type_id, description, picture)" +
            " VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_GOOD_SQL = "UPDATE goods SET name = ?, price = ?, type_id = ?, description = ?, " +
            "picture = ? WHERE id = ?";
    private static final String GOOD_TYPE_ID = "good_type_id";
    private final DaoMapper daoMapper;
    private Connection connection;

    public JWDGoodDao() {
        daoMapper = new DaoMapper();
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Good> findAll() throws DaoException {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_GOODS_SQL);
            List<Good> goods = new ArrayList<>();

            while (!resultSet.isAfterLast()) {
                goods.add(daoMapper.mapGood(resultSet));
            }

            return goods;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public Optional<Good> findById(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_GOOD_BY_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Good good = daoMapper.mapGood(resultSet);

            return good.getId() != 0 ? Optional.of(good) : Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public boolean add(Good good) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_GOOD_SQL)) {
            fillGoodData(good, statement);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public boolean removeById(Long id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(REMOVE_GOOD_BY_ID_SQL)) {
            statement.setLong(1, id);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public boolean update(Good good) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_GOOD_SQL)) {
            statement.setLong(6, good.getId());
            fillGoodData(good, statement);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }


    private int findTypeIDByName(String typeName) throws DaoException, SQLException {
        try (PreparedStatement statement = connection.prepareStatement(FIND_TYPE_BY_NAME_SQL)) {
            statement.setString(1, typeName);
            ResultSet resultSet = statement.executeQuery();
            int typeId = 0;

            while (resultSet.next()) {
                typeId = resultSet.getInt(GOOD_TYPE_ID);
            }

            if (typeId == 0) {
                throw new DaoException(String.format(UNKNOWN_TYPE_ERROR_MESSAGE, typeName));
            }

            return typeId;
        }
    }

    private void fillGoodData(Good good, PreparedStatement statement) throws SQLException, DaoException {
        int typeID = findTypeIDByName(good.getType());
        statement.setString(1, good.getName());
        statement.setBigDecimal(2, good.getPrice());
        statement.setInt(3, typeID);
        statement.setString(4, good.getDescription());
        statement.setString(5, good.getImgURL());
    }
}