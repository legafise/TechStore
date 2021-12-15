package by.lashkevich.logic.service.impl;

import by.lashkevich.logic.dao.OrderDao;
import by.lashkevich.logic.dao.impl.JWDOrderDao;
import by.lashkevich.logic.entity.*;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.validator.OrderValidator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JWDOrderServiceTest {
    private JWDOrderService orderService;
    private OrderDao orderDao;
    private OrderValidator orderValidator;
    private Order firstTestOrder;
    private Order secondTestOrder;

    @BeforeEach
    void setUp() {
        orderService = new JWDOrderService();
        orderDao = mock(JWDOrderDao.class);
        orderValidator = mock(OrderValidator.class);
        orderService.setOrderDao(orderDao);
        orderService.setOrderValidator(orderValidator);

        GoodType firstTestType = new GoodType((short) 1, "Phone");
        GoodType secondTestType = new GoodType((short) 2, "Console");

        Good firstTestGood = new Good(1, "Iphone", new BigDecimal("1500"), "Iphone",
                firstTestType, "default.jpg");
        Good secondTestGood = new Good(2, "Play Station 5", new BigDecimal("2000"),
                "Play Station 5", secondTestType, "default.jpg");

        Map<Good, Short> firstTestGoods = new HashMap<>();
        firstTestGoods.put(firstTestGood, (short) 1);
        firstTestGoods.put(secondTestGood, (short) 1);

        Map<Good, Short> secondTestGoods = new HashMap<>();
        firstTestGoods.put(firstTestGood, (short) 1);

        User customer = new User(1, "Roman", "Lash", "legafise", "12345678",
                "lash@ya.ru", LocalDate.now(), "default.jpg", new BigDecimal("50.34"), Role.USER);

        firstTestOrder = new Order(1, OrderStatus.EXECUTING, "Minsk", firstTestGoods, new BigDecimal("3500"),
                LocalDate.now(), customer);
        secondTestOrder = new Order(2, OrderStatus.IN_PROCESSING, "Gomel", secondTestGoods,
                new BigDecimal("1500"), LocalDate.now(), customer);
    }

    @Test
    void findAllOrdersTest() {
        List<Order> orderList = Arrays.asList(firstTestOrder, secondTestOrder);
        when(orderDao.findAll()).thenReturn(orderList);
        Assert.assertEquals(orderService.findAllOrders(), orderList);
    }

    @Test
    void findByIdTest() {
        when(orderDao.findById(1L)).thenReturn(Optional.of(firstTestOrder));
        Assert.assertEquals(orderService.findById("1"), firstTestOrder);
    }

    @Test
    void findByIdWithInvalidIdTest() {
        Assert.assertThrows(ServiceException.class, () -> orderService.findById("wfwc"));
    }

    @Test
    void updateOrderTest() {
        firstTestOrder.setStatus(OrderStatus.IN_PROCESSING);
        when(orderDao.update(firstTestOrder)).thenReturn(true);
        when(orderValidator.validate(firstTestOrder)).thenReturn(true);
        Assert.assertTrue(orderService.updateOrder(firstTestOrder));
    }

    @Test
    void findOrdersByUserIdTest() {
        List<Order> orderList = Arrays.asList(firstTestOrder, secondTestOrder);
        when(orderDao.findOrdersByUserId(1L)).thenReturn(Optional.of(orderList));
        Assert.assertEquals(orderService.findOrdersByUserId("1"), Optional.of(orderList));
    }

    @Test
    void findOrdersByUserIdWithInvalidIdTest() {
        Assert.assertThrows(ServiceException.class, () -> orderService.findOrdersByUserId("eveve"));
    }
}