package com.biblioteca.biblioteca.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca.domain.dto.LivroDTO;
import com.biblioteca.biblioteca.domain.service.ILivroService;
import com.biblioteca.biblioteca.shared.CustomException;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/livros")
@Tag(name = "Livros", description = "APIs relacionadas a livros")
public class LivroController {
    @Autowired
    private ILivroService livroService;

    @PostMapping
    public ResponseEntity<LivroDTO> cadastrarLivro(@Valid @RequestBody LivroDTO livro) {
        try {
            LivroDTO result = livroService.cadastrarLivro(livro);
            return ResponseEntity.status(201).body(result); // Retorna 201 Created
        } catch (Exception e) {
            // Log o erro para diagnosticar o problema
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null); // Retorna 400 Bad Request
        }
    }

    @GetMapping
    public ResponseEntity<List<LivroDTO>> listarLivros() {
        try {
            List<LivroDTO> livros = livroService.listarTodosLivros();
            return ResponseEntity.ok(livros);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> buscarLivroPorId(@PathVariable Long id) {
        try {
            LivroDTO livro = livroService.buscarPorId(id);
            return ResponseEntity.ok(livro);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> atualizarLivro(@PathVariable Long id, @RequestBody LivroDTO livro) {
        try {
            LivroDTO updatedLivro = livroService.atualizarLivro(id, livro);
            return ResponseEntity.ok(updatedLivro);
        } catch (CustomException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerLivro(@PathVariable Long id) {
        try {
            livroService.removerLivro(id);
            return ResponseEntity.noContent().build();
        } catch (CustomException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
