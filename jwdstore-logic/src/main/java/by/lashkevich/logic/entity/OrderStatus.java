package by.lashkevich.logic.entity;

public enum OrderStatus {
    IN_PROCESSING("The order is being processed"),
    EXECUTING("The order is being executed"),
    COMPLETED("The order is completed");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
