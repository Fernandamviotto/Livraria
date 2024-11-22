package com.biblioteca.biblioteca.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "livro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livro")
    private Long id;

    @Column(name = "titulo_livro")
    private String titulo;

    @Column(name = "autor_livro")
    private String autor;

    @Column(name = "editora_livro")
    private String editora;

    @Column(name = "ano_publicacao")
    private int anoPublicacao;

    @Column(name = "disponibilidade_livro")
    private boolean disponibilidade;
}