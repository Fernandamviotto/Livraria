package com.biblioteca.biblioteca.shared;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {
    private final HttpStatus status; // Código de status HTTP associado à exceção
    private final String details; // Detalhes adicionais sobre o erro

    // Construtor com status e detalhes
    public CustomException(String message, HttpStatus status, String details) {
        super(message);
        this.status = status;
        this.details = details;
    }

    // Construtor com status apenas
    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.details = null; // Detalhes adicionais opcionais
    }

    // Getters
    public HttpStatus getStatus() {
        return status;
    }

    public String getDetails() {
        return details;
    }
}
