package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.Cliente;

public class EmailNotificationServiceImpl implements NotificationService {
    @Override
    public void enviarNotificacao(Cliente cliente, String mensagem) {
        System.out.println("Enviando e-mail para " + cliente.getEmail() + ": " + mensagem);
    }
}

