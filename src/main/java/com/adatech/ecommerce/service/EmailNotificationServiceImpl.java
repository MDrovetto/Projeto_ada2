package main.java.com.adatech.ecommerce.service;

import main.java.com.adatech.ecommerce.model.Cliente;

/**
 * Implementação do serviço de notificação que simula o envio de e-mails.
 * TODO:
 *  - Implementar o método enviarNotificacao.
 *  - A implementação deve apenas imprimir uma mensagem no console, simulando o envio
 *    de um e-mail para o cliente. Ex: "Enviando e-mail para [email_cliente]: [mensagem]".
 */
public class EmailNotificationServiceImpl implements NotificationService {
    @Override
    public void enviarNotificacao(Cliente cliente, String mensagem) {
        // TODO: Implementar a simulação de envio de e-mail.
        // Implementa a simulação de envio de e-mail
        System.out.println("Enviando e-mail para " + cliente.getEmail() + ": " + mensagem);
    }
}

