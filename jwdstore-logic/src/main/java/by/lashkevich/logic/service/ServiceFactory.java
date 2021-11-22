package by.lashkevich.logic.service;

import by.lashkevich.logic.service.impl.JWDGoodService;
import by.lashkevich.logic.service.impl.JWDUserService;

public enum ServiceFactory {
    GOOD_SERVICE(new JWDGoodService()),
    USER_SERVICE(new JWDUserService());

    private final Service<?> service;

    ServiceFactory(Service<?> service) {
        this.service = service;
    }

    public Service<?> getService() {
        return service;
    }
}