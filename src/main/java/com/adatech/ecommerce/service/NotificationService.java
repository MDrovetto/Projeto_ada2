package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.Cliente;

public interface NotificationService {
    void enviarNotificacao(Cliente cliente, String mensagem);
}

