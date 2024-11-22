package com.biblioteca.biblioteca.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca.domain.entity.Livro;

public interface ILivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByDisponibilidadeTrue();
}
