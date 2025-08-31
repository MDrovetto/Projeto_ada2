package main.java.com.adatech.ecommerce.service;

import main.java.com.adatech.ecommerce.model.Cliente;

/**
 * Interface para serviços de notificação.
 * Define um contrato para enviar notificações aos clientes.
 * TODO:
 *  - Definir o método:
 *    - enviarNotificacao(Cliente cliente, String mensagem): void
 */
public interface NotificationService {
    void enviarNotificacao(Cliente cliente, String mensagem);
}

