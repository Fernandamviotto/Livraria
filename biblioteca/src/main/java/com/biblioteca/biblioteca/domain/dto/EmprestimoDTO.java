package com.biblioteca.biblioteca.domain.dto;

import java.time.LocalDate;

import com.biblioteca.biblioteca.shared.StatusEmprestimo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmprestimoDTO {
    private Long id; // ID do empréstimo

    private UsuarioDTO usuarioDTO; // Referência ao DTO do usuário

    private LivroDTO livroDTO; // Referência ao DTO do livro

    private LocalDate dataEmprestimo; // Data do empréstimo

    private LocalDate dataDevolucaoPrevista; // Data de devolução prevista

    private LocalDate dataDevolucaoRealizada; // Data de devolução realizada

    private StatusEmprestimo status; // Status do empréstimo (ativo, concluído, em atraso)

    private Double multaAplicada; 
}
