package notification;

public class ConsoleNotificationService implements NotificationService {
    @Override
    public void enviarNotificacao(String mensagem) {
        System.out.println("🔔 NOTIFICAÇÃO: " + mensagem);
    }
}