package com.biblioteca.biblioteca.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca.application.Mappers;
import com.biblioteca.biblioteca.domain.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.domain.entity.Emprestimo;
import com.biblioteca.biblioteca.domain.repository.IEmprestimoRepository;
import com.biblioteca.biblioteca.domain.repository.IReservaRepository;
import com.biblioteca.biblioteca.domain.service.IEmprestimoService;
import com.biblioteca.biblioteca.shared.CustomException;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/emprestimo")
@Tag(name = "Emprestimos", description = "APIs relacionadas a emprestimos")
public class EmprestimoController {
    @Autowired
    private IEmprestimoService emprestimoService;

    @Autowired
    private IEmprestimoRepository emprestimoRepository; // Repositório de empréstimos

    @Autowired
    private IReservaRepository reservaRepository; // Repositório de reservas

    @Autowired
    private Mappers emprestimoMapper;

    // Sera o metodo post
    @PostMapping
    public ResponseEntity<EmprestimoDTO> cadastrarEmprestimo(@RequestBody @Valid EmprestimoDTO emprestimoDTO) {
        EmprestimoDTO novoEmprestimo = emprestimoService.cadastrarEmprestimo(emprestimoDTO);

        return ResponseEntity.status(201).body(novoEmprestimo); // Retorna 201 Created
    }

    @PutMapping("/{id}/devolucao")
    public ResponseEntity<EmprestimoDTO> registrarDevolucao(@PathVariable Long id,
            @RequestBody @Valid EmprestimoDTO registrarDevolucao) {
        EmprestimoDTO emprestimoDevolvido = emprestimoService.registrarDevolucao(id, registrarDevolucao);

        return ResponseEntity.ok(emprestimoDevolvido);
    }

    @PutMapping("/{id}/renovacao")
    public ResponseEntity<EmprestimoDTO> renovarEmprestimo(@PathVariable Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new CustomException("Empréstimo não encontrado"));

        // Verifica se o livro está reservado por outro usuário
        boolean reservado = reservaRepository.existsByLivro_IdAndAtivoTrue(emprestimo.getLivro().getId());
        if (reservado) {
            throw new CustomException("O livro está reservado por outro usuário e não pode ser renovado.");
        }

        emprestimo.setDataDevolucaoPrevista(emprestimo.getDataDevolucaoPrevista().plusDays(14));
        emprestimoRepository.save(emprestimo);

        return ResponseEntity.ok(emprestimoMapper.EmprestimotoDto(emprestimo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmprestimoDTO> consultarHistoricoPorUsuario(@PathVariable Long id) {
        try {
            EmprestimoDTO emprestimo = (EmprestimoDTO) emprestimoService.consultarHistoricoPorUsuario(id);
            return ResponseEntity.ok(emprestimo);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{idUsuario}")
    public ResponseEntity<EmprestimoDTO> consultarHistoricoPorLivro(@PathVariable Long idUsuario) {
        try {
            EmprestimoDTO emprestimo = (EmprestimoDTO) emprestimoService.consultarHistoricoPorLivro(idUsuario);
            return ResponseEntity.ok(emprestimo);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
