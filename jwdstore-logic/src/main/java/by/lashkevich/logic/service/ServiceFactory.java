package by.lashkevich.logic.service;

import by.lashkevich.logic.service.impl.JWDGoodService;
import by.lashkevich.logic.service.impl.JWDOrderService;
import by.lashkevich.logic.service.impl.JWDReviewService;
import by.lashkevich.logic.service.impl.JWDUserService;

public enum ServiceFactory {
    REVIEW_SERVICE(new JWDReviewService()),
    GOOD_SERVICE(new JWDGoodService()),
    USER_SERVICE(new JWDUserService()),
    ORDER_SERVICE(new JWDOrderService());

    private final Service service;

    ServiceFactory(Service service) {
        this.service = service;
    }

    public Service getService() {
        return service;
    }
}