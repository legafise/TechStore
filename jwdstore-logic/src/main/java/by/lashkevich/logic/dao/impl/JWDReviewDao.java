package by.lashkevich.logic.dao.impl;

import by.lashkevich.logic.dao.DaoException;
import by.lashkevich.logic.dao.ReviewDao;
import by.lashkevich.logic.dao.mapper.DaoMapper;
import by.lashkevich.logic.dao.pool.ConnectionPool;
import by.lashkevich.logic.entity.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Jwd review dao.
 * @author Roman Lashkevich
 * @see ReviewDao
 */
public class JWDReviewDao implements ReviewDao {
    private static final String FIND_ALL_REVIEWS_SQL = "SELECT reviews.id AS review_id, reviews.rate," +
            " reviews.content, reviews.id AS review_id, reviews.rate, reviews.content, users.id AS review_user_id," +
            " users.name AS review_user_name, users.surname AS review_user_surname, users.login AS review_user_login, " +
            " users.password AS review_user_password, users.email AS review_user_email, users.profile_picture AS" +
            " review_user_profile_picture, users.birth_date AS review_user_birth_date, users.balance AS" +
            " review_user_balance, users.role AS review_user_role" +
            " FROM reviews INNER JOIN users ON users.id = reviews.user_id";
    private static final String FIND_REVIEWS_BY_USER_ID_SQL = "SELECT reviews.id AS review_id, reviews.rate," +
            " reviews.content, users.id AS review_user_id, users.name AS review_user_name, users.surname AS review_user_surname," +
            " users.login AS review_user_login, users.password AS review_user_password, users.email AS review_user_email, users.profile_picture AS" +
            " review_user_profile_picture, users.birth_date AS review_user_birth_date, users.balance AS" +
            " review_user_balance, users.role AS review_user_role" +
            " FROM reviews INNER JOIN users ON users.id = reviews.user_id WHERE reviews.user_id = ?";
    private static final String FIND_REVIEW_BY_USER_AND_GOOD_ID_SQL = "SELECT reviews.id AS review_id, reviews.rate," +
            " reviews.content, users.id AS review_user_id, users.name AS review_user_name, users.surname AS review_user_surname, users.login AS review_user_login, " +
            " users.password AS review_user_password, users.email AS review_user_email, users.profile_picture AS" +
            " review_user_profile_picture, users.birth_date AS review_user_birth_date, users.balance AS" +
            " review_user_balance, users.role AS review_user_role, goods_reviews.good_id" +
            " FROM reviews INNER JOIN users ON users.id = reviews.user_id INNER JOIN goods_reviews ON" +
            " reviews.id = goods_reviews.review_id WHERE reviews.user_id = ? AND goods_reviews.good_id = ?";
    private static final String FIND_REVIEW_BY_ID_SQL = "SELECT reviews.id AS review_id, reviews.rate, reviews.content," +
            " users.id AS review_user_id," +
            " users.name AS review_user_name, users.surname AS review_user_surname, users.login AS review_user_login, " +
            " users.password AS review_user_password, users.email AS review_user_email, users.profile_picture AS" +
            " review_user_profile_picture, users.birth_date AS review_user_birth_date, users.balance AS" +
            " review_user_balance, users.role AS review_user_role" +
            " FROM reviews INNER JOIN users ON users.id = reviews.user_id WHERE reviews.id = ?";
    private static final String REMOVE_REVIEW_BY_ID_SQL = "DELETE FROM reviews WHERE id = ?";
    private static final String CREATE_REVIEW_SQL = "INSERT INTO reviews (rate, content, user_id) VALUES (?, ?, ?)";
    private static final String UPDATE_REVIEW_SQL = "UPDATE reviews SET rate = ?, content = ?," +
            " user_id = ? WHERE id = ?";
    private static final String CONNECT_REVIEW_TO_GOOD = "INSERT INTO goods_reviews (review_id, good_id) VALUES (?, ?)";
    private static final String REMOVE_CONNECTION_BETWEEN_REVIEW_AND_GOOD = "DELETE FROM goods_reviews" +
            " WHERE goods_reviews.review_id = ?";
    private final DaoMapper mapper;

    /**
     * Instantiates a new Jwd review dao.
     */
    public JWDReviewDao() {
        this.mapper = new DaoMapper();
    }

    @Override
    public List<Review> findAll()  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_REVIEWS_SQL);
            List<Review> reviews = new ArrayList<>();

            while (resultSet.next()) {
                reviews.add(mapper.mapReview(resultSet));
            }

            return reviews;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Review> findById(Long id)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_REVIEW_BY_ID_SQL)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Review review = new Review();

            while (resultSet.next()) {
                review = mapper.mapReview(resultSet);
            }

            return review.getId() != 0 ? Optional.of(review) : Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean add(Review review)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_REVIEW_SQL)) {
            fillReviewData(review, statement);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean removeById(Long id)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_REVIEW_BY_ID_SQL)) {
            statement.setLong(1, id);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Review review)  {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_REVIEW_SQL)) {
            statement.setLong(4, review.getId());
            fillReviewData(review, statement);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean connectReviewToGood(Long reviewId, Long goodId) {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(CONNECT_REVIEW_TO_GOOD)) {
            statement.setLong(1, reviewId);
            statement.setLong(2, goodId);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Review> findByUserId(Long userId) {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_REVIEWS_BY_USER_ID_SQL)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<Review> reviews = new ArrayList<>();

            while (resultSet.next()) {
                reviews.add(mapper.mapReview(resultSet));
            }

            return reviews;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean removeConnectionBetweenReviewAndGood(Long reviewId) {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_CONNECTION_BETWEEN_REVIEW_AND_GOOD)) {
            statement.setLong(1, reviewId);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Review> findReviewByUserAndGoodId(Long userId, Long goodId) {
        try (Connection connection = ConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_REVIEW_BY_USER_AND_GOOD_ID_SQL)) {
            statement.setLong(1, userId);
            statement.setLong(2, goodId);
            ResultSet resultSet = statement.executeQuery();
            Review review = new Review();

            while (resultSet.next()) {
                review = mapper.mapReview(resultSet);
            }

            return review.getId() != 0 ? Optional.of(review) : Optional.empty();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void fillReviewData(Review review, PreparedStatement statement) throws SQLException {
        statement.setShort(1, review.getRate());
        statement.setString(2, review.getContent());
        statement.setLong(3, review.getAuthor().getId());
    }
}
