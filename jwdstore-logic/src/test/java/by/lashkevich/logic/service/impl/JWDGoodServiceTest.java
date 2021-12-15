package by.lashkevich.logic.service.impl;

import by.lashkevich.logic.dao.GoodDao;
import by.lashkevich.logic.entity.*;
import by.lashkevich.logic.service.OrderService;
import by.lashkevich.logic.service.ServiceException;
import by.lashkevich.logic.service.validator.GoodValidator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JWDGoodServiceTest {
    private JWDGoodService goodService;
    private GoodValidator goodValidator;
    private GoodDao goodDao;
    private GoodType firstTestType;
    private GoodType secondTestType;
    private Good firstTestGood;
    private Good secondTestGood;

    @BeforeEach
    void setUp() {
        goodValidator = mock(GoodValidator.class);
        goodService = new JWDGoodService();
        goodDao = mock(GoodDao.class);
        goodService.setGoodDao(goodDao);
        firstTestType = new GoodType((short) 1, "Phone");
        secondTestType = new GoodType((short) 2, "Console");
        firstTestGood = new Good(1,"Iphone", new BigDecimal("1500"), "Iphone",
                firstTestType, "default.jpg");
        secondTestGood = new Good(2, "Play Station 5", new BigDecimal("2000"),
                "Play Station 5", secondTestType, "default.jpg");
    }

    @Test
    void findAllGoodsTest() {
        List<Good> goodList = Arrays.asList(firstTestGood, secondTestGood);
        when(goodDao.findAll()).thenReturn(goodList);
        Assert.assertEquals(goodService.findAllGoods(), goodList);
    }

    @Test
    void findAllGoodTypesTest() {
        List<GoodType> goodTypes = Arrays.asList(firstTestType, secondTestType);
        when(goodDao.findAllTypes()).thenReturn(goodTypes);
        Assert.assertEquals(goodService.findAllGoodTypes(), goodTypes);
    }

    @Test
    void findGoodByIdTest() {
        when(goodDao.findById(1L)).thenReturn(Optional.of(firstTestGood));
        Assert.assertEquals(goodService.findGoodById("1"), firstTestGood);
    }

    @Test
    void findGoodByIdWithInvalidIdTest() {
        Assert.assertThrows(ServiceException.class, () -> goodService.findGoodById("qwdqdq"));
    }

    @Test
    void findTypeByIdTest() {
        when(goodDao.findTypeById(1)).thenReturn(Optional.of(firstTestType));
        Assert.assertEquals(goodService.findTypeById("1"), firstTestType);
    }

    @Test
    void findTypeByIdWithInvalidIdTest() {
        Assert.assertThrows(ServiceException.class, () -> goodService.findTypeById("wefwww"));
    }

    @Test
    void isBoughtGoodTest() {
        Map<Good, Short> goods = new HashMap<>();
        goods.put(firstTestGood, (short) 1);
        goods.put(secondTestGood, (short) 1);
        User customer = new User(1, "Roman", "Lash","legafise", "12345678",
                "lash@ya.ru", LocalDate.now(), "default.jpg", new BigDecimal("50.34"), Role.USER);
        Order order = new Order(1, OrderStatus.COMPLETED, "Minsk", goods, new BigDecimal("3500"),
                LocalDate.now(), customer);
        OrderService orderService = mock(JWDOrderService.class);
        goodService.setOrderService(orderService);
        when(orderService.findOrdersByUserId("1")).thenReturn(Optional.of(Collections.singletonList(order)));
        Assert.assertTrue(goodService.isBoughtGood("1", "1"));
    }

    @Test
    void addGoodTest() {
        goodService.setGoodValidator(goodValidator);
        when(goodDao.add(firstTestGood)).thenReturn(true);
        when(goodValidator.validate(firstTestGood)).thenReturn(true);
        Assert.assertTrue(goodService.addGood(firstTestGood));
    }

    @Test
    void updateGoodTest() {
        firstTestGood.setName("Iphone12");
        goodService.setGoodValidator(goodValidator);
        when(goodDao.update(firstTestGood)).thenReturn(true);
        when(goodValidator.validate(firstTestGood)).thenReturn(true);
        Assert.assertTrue(goodService.updateGood(firstTestGood));
    }
}