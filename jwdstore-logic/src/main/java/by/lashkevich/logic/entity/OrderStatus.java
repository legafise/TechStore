package by.lashkevich.logic.entity;

import java.util.Arrays;

/**
 * The enum Order status.
 * @author Roman Lashkevich
 */
public enum OrderStatus {
    /**
     * The Executing.
     */
    EXECUTING("The order is being executed"),
    /**
     * The In processing.
     */
    IN_PROCESSING("The order is being processed"),
    /**
     * The Completed.
     */
    COMPLETED("The order is completed");

    private static final String UNKNOWN_STATUS_MESSAGE = "Unknown order status";
    private final String statusContent;

    OrderStatus(String statusContent) {
        this.statusContent = statusContent;
    }

    /**
     * Gets status content.
     *
     * @return the status content
     */
    public String getStatusContent() {
        return statusContent;
    }

    /**
     * Find status order status.
     *
     * @param statusContent the status content
     * @return the order status
     * @throws OrderStatusException the order status exception
     */
    public static OrderStatus findStatus(String statusContent) throws OrderStatusException {
        return Arrays.stream(OrderStatus.values())
                .filter(currentStatus -> currentStatus.getStatusContent().equals(statusContent))
                .findFirst()
                .orElseThrow(() -> new OrderStatusException(UNKNOWN_STATUS_MESSAGE));
    }
}
