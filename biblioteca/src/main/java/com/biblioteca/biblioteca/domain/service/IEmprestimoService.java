package com.biblioteca.biblioteca.domain.service;

import java.util.List;

import com.biblioteca.biblioteca.domain.dto.EmprestimoDTO;

public interface IEmprestimoService {
    // Realizar e registrar empréstimos de livros
    EmprestimoDTO cadastrarEmprestimo(EmprestimoDTO emprestimoDTO);

    // Registrar devoluções
    EmprestimoDTO registrarDevolucao(Long id, EmprestimoDTO registrarDevolucao);

    // Consultar histórico de empréstimos por usuário
    List<EmprestimoDTO> consultarHistoricoPorUsuario(Long id);

    // Consultar histórico de empréstimos por livro
    List<EmprestimoDTO> consultarHistoricoPorLivro(Long id);

    // // Consultar empréstimos em atraso
    // List<EmprestimoDTO> consultarEmprestimosEmAtraso();
}
