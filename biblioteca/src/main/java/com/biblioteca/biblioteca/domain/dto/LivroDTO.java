package com.biblioteca.biblioteca.domain.dto;

import lombok.Data;

@Data
public class LivroDTO {
    private Long id;
    private String titulo;
    private String autor;
    private String editora;
    private int anoPublicacao;
    private boolean disponibilidade;
}
