package by.lashkevich.logic.service;

import by.lashkevich.logic.service.impl.JWDGoodService;
import by.lashkevich.logic.service.impl.JWDOrderService;
import by.lashkevich.logic.service.impl.JWDReviewService;
import by.lashkevich.logic.service.impl.JWDUserService;

/**
 * The enum Service factory.
 * @author Roman Lashkevich
 */
public enum ServiceFactory {
    /**
     * The Review service.
     */
    REVIEW_SERVICE(new JWDReviewService()),
    /**
     * The User service.
     */
    USER_SERVICE(new JWDUserService()),
    /**
     * The Order service.
     */
    ORDER_SERVICE(new JWDOrderService()),
    /**
     * The Good service.
     */
    GOOD_SERVICE(new JWDGoodService());

    private final Service service;

    ServiceFactory(Service service) {
        this.service = service;
    }

    /**
     * Gets service.
     *
     * @return the service
     */
    public Service getService() {
        return service;
    }
}