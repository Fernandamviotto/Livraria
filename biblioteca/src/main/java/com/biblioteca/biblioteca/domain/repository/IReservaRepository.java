package com.biblioteca.biblioteca.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.biblioteca.domain.entity.Reserva;

public interface IReservaRepository extends JpaRepository<Reserva, Long> {
    boolean existsByLivro_IdAndAtivoTrue(Long livroId);
}

