package com.adatech.ecommerce.exception;

public class PedidoVazioException extends RegraDeNegocioException {

    // É comum que exceções específicas de regras de negócio herdem da exceção de regra mais genérica
    public PedidoVazioException(String message) {
        super(message);
    }

    public PedidoVazioException(String message, Throwable cause) {
        super(message, cause);
    }
}