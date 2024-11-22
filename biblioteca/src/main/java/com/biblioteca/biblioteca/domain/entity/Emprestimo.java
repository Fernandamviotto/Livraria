package com.biblioteca.biblioteca.domain.entity;

import java.time.LocalDate;

import com.biblioteca.biblioteca.shared.StatusEmprestimo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "emprestimo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Emprestimo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Usando auto increment
    private Long id; // ID do empréstimo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Relacionamento com Usuário

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro; // Relacionamento com Livro

    @Column(name = "data_emprestimo", nullable = false)
    private LocalDate dataEmprestimo; // Data do empréstimo

    @Column(name = "data_devolucao_prevista", nullable = false)
    private LocalDate dataDevolucaoPrevista; // Data de devolução prevista

    @Column(name = "data_devolucao_realizada")
    private LocalDate dataDevolucaoRealizada; // Data de devolução realizada

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEmprestimo status; // Status do empréstimo (ativo, concluído, em atraso)

    @Column(name = "multa_aplicada")
    private Double multaAplicada;
}
