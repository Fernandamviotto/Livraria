package com.biblioteca.biblioteca.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca.domain.entity.Emprestimo;

public interface IEmprestimoRepository extends JpaRepository<Emprestimo, Long>{
    Optional<Emprestimo> findByUsuario_Id(Long usuarioId);

    Optional<Emprestimo> findByLivro_Id(Long livroId);
}
