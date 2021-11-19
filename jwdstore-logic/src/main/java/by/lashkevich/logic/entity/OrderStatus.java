package by.lashkevich.logic.entity;

import java.util.Arrays;

public enum OrderStatus {
    IN_PROCESSING("The order is being processed"),
    EXECUTING("The order is being executed"),
    COMPLETED("The order is completed");

    private static final String UNKNOWN_STATUS_MESSAGE = "Unknown order status";
    private final String statusContent;

    OrderStatus(String statusContent) {
        this.statusContent = statusContent;
    }

    public String getStatusContent() {
        return statusContent;
    }

    public static OrderStatus findStatus(String statusContent) throws OrderStatusException {
        return Arrays.stream(OrderStatus.values())
                .filter(currentStatus -> currentStatus.getStatusContent().equals(statusContent))
                .findFirst()
                .orElseThrow(() -> new OrderStatusException(UNKNOWN_STATUS_MESSAGE));
    }
}
