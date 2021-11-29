package by.lashkevich.web.controller.command;

import by.lashkevich.logic.dao.*;
import by.lashkevich.logic.dao.pool.ConnectionPool;
import by.lashkevich.logic.entity.*;
import by.lashkevich.logic.service.OrderService;
import by.lashkevich.logic.service.ReviewService;
import by.lashkevich.logic.service.ServiceFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws DaoException {
        ConnectionPool.getInstance().initializeConnectionPool(1);
        OrderService orderService = (OrderService) ServiceFactory.ORDER_SERVICE.getService();
        ReviewService reviewService = (ReviewService) ServiceFactory.REVIEW_SERVICE.getService();
        OrderDao orderDao = (OrderDao) DaoFactory.ORDER_DAO.getDao();
        GoodDao goodDao = (GoodDao) DaoFactory.GOOD_DAO.getDao();
        ReviewDao reviewDao = (ReviewDao) DaoFactory.REVIEW_DAO.getDao();
        User user = new User();
        user.setId(10);
        Review review = new Review();
        review.setAuthor(user);
        review.setContent("This is the best TV that I bought ever!");
        review.setRate((short) 5);
//        Good firstGood = new Good();
//        Good secondGood = new Good();
//        firstGood.setId(6);
//        secondGood.setId(7);
//        Order order = new Order();
//        order.setDate(LocalDate.now());
//        order.setAddress("Minsk, Knorina 12");
//        order.setPrice(new BigDecimal("2324.5"));
//        order.setStatus(OrderStatus.EXECUTING);
//        order.setCustomer(user);
//        Map<Good, Integer> goods = new HashMap<>();
//        goods.put(firstGood, 1);
//        goods.put(secondGood, 1);
//        order.setGoods(goods);
//        System.out.println(orderService.addOrder(order));
        System.out.println(orderService.findOrdersByUserId("18"));
    }
}
