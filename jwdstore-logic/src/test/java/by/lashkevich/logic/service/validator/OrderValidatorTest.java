package by.lashkevich.logic.service.validator;

import by.lashkevich.logic.entity.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderValidatorTest {
    private OrderValidator orderValidator;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        orderValidator = new OrderValidator();

        GoodType firstTestType = new GoodType(1, "Phone");
        GoodType secondTestType = new GoodType(2, "Console");

        Good firstTestGood = new Good(1, "Iphone", new BigDecimal("1500"), "Iphone",
                firstTestType, "default.jpg");
        Good secondTestGood = new Good(2, "Play Station 5", new BigDecimal("2000"),
                "Play Station 5", secondTestType, "default.jpg");

        Map<Good, Integer> firstTestGoods = new HashMap<>();
        firstTestGoods.put(firstTestGood, 1);
        firstTestGoods.put(secondTestGood, 1);

        User customer = new User(1, "Roman", "Lash", "legafise", "12345678",
                "lash@ya.ru", LocalDate.now(), "default.jpg", new BigDecimal("50.34"), Role.USER);

        testOrder = new Order(1, OrderStatus.EXECUTING, "Minsk, Meleza 4, kv 15", firstTestGoods, new BigDecimal("3500"),
                LocalDate.now(), customer);
    }

    @Test
    void validateRightOrderTest() {
        Assert.assertTrue(orderValidator.test(testOrder));
    }

    @Test
    void validateOrderWithNullUserTest() {
        testOrder.setCustomer(null);
        Assert.assertFalse(orderValidator.test(testOrder));
    }

    @Test
    void validateOrderWithNullStatusTest() {
        testOrder.setStatus(null);
        Assert.assertFalse(orderValidator.test(testOrder));
    }

    @Test
    void validateOrderWithNullDateTest() {
        testOrder.setDate(null);
        Assert.assertFalse(orderValidator.test(testOrder));
    }

    @Test
    void validateOrderWithEmptyGoodsTest() {
        testOrder.setGoods(new HashMap<>());
        Assert.assertFalse(orderValidator.test(testOrder));
    }

    @Test
    void validateOrderWithNullPriceTest() {
        testOrder.setPrice(null);
        Assert.assertFalse(orderValidator.test(testOrder));
    }

    @Test
    void validateOrderWithInvalidAddressTest() {
        testOrder.setAddress("Grodno");
        Assert.assertFalse(orderValidator.test(testOrder));
    }
}