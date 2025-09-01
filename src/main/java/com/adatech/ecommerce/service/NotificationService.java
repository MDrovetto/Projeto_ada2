package com.adatech.ecommerce.service;

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

