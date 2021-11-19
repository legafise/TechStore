package by.lashkevich.logic.service;

import by.lashkevich.logic.service.impl.JWDGoodService;

public enum ServiceFactory {
    GOOD_SERVICE(new JWDGoodService());

    private final Service<?> service;

    ServiceFactory(Service<?> service) {
        this.service = service;
    }

    public Service<?> getService() {
        return service;
    }
}