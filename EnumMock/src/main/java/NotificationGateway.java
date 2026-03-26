public interface NotificationGateway {
    void send(String destination, String message);
}