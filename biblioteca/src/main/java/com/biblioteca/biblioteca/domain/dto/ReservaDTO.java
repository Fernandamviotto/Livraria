package com.biblioteca.biblioteca.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservaDTO {
    private Long id; // ID do empréstimo

    private UsuarioDTO usuarioDTO; // Referência ao DTO do usuário

    private LivroDTO livroDTO;

    private Boolean ativo; // Status da reserva
}
