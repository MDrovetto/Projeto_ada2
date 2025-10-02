package com.adatech.ecommerce.exception;

// Herdar de RuntimeException significa que o compilador não obriga o método a declarar "throws"
// mas quem chama o método pode optar por tratar a exceção.
public class RegraDeNegocioException extends RuntimeException {

    // Construtor que recebe a mensagem de erro
    public RegraDeNegocioException(String message) {
        super(message);
    }

    // Opcional: Construtor que recebe a mensagem e a exceção original (causa)
    public RegraDeNegocioException(String message, Throwable cause) {
        super(message, cause);
    }
}