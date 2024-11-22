package com.biblioteca.biblioteca.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca.domain.entity.Emprestimo;
import com.biblioteca.biblioteca.shared.StatusEmprestimo;

public interface IEmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    Optional<Emprestimo> findByUsuario_Id(Long usuarioId);

    Optional<Emprestimo> findByLivro_Id(Long livroId);

    long countByUsuario_IdAndStatus(Long usuarioId, StatusEmprestimo status);

    boolean existsByLivro_IdAndStatus(Long livroId, StatusEmprestimo status);
}
