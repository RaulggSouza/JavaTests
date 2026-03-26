import java.util.Objects;
import java.util.UUID;

public class OrderNotificationService {
    private final CustomerRepository customer_repository;
    private final NotificationGateway notification_gateway;

    public OrderNotificationService(
            CustomerRepository customer_repository,
            NotificationGateway notification_gateway
    ) {
        this.customer_repository = customer_repository;
        this.notification_gateway = notification_gateway;
    }

    public void notifyOrderStatus(UUID customer_id, OrderStatus status) {
        Objects.requireNonNull(customer_id, "customer_id cannot be null");
        Objects.requireNonNull(status, "status cannot be null");

        Customer customer = customer_repository.findById(customer_id)
                .orElseThrow(() -> new IllegalArgumentException("customer not found"));

        String message = switch (status) {
            case CREATED -> "Your order has been created.";
            case PAID -> "Your order has been paid.";
            case SHIPPED -> "Your order has been shipped.";
            case DELIVERED -> "Your order has been delivered.";
            case CANCELLED -> "Your order has been cancelled.";
        };

        notification_gateway.send(customer.email(), message);
    }
}
