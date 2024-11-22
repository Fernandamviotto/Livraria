package com.biblioteca.biblioteca.application.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca.application.Mappers;
import com.biblioteca.biblioteca.domain.dto.LivroDTO;
import com.biblioteca.biblioteca.domain.entity.Livro;
import com.biblioteca.biblioteca.domain.repository.ILivroRepository;
import com.biblioteca.biblioteca.domain.service.ILivroService;
import com.biblioteca.biblioteca.shared.CustomException;

@Service
public class LivroService implements ILivroService {
    @Autowired
    private ILivroRepository livroRepository;

    @Autowired
    private Mappers livroMapper;

    // Cadastrar um novo livro
    @Override
    public LivroDTO cadastrarLivro(LivroDTO livroDTO) {
        Livro livro = livroMapper.LivroDTOtoEntity(livroDTO);
        livro = livroRepository.save(livro);
        return livroMapper.livrtoDto(livro);
    }

    // Atualizar um livro existente
    @Override
    public LivroDTO atualizarLivro(Long id, LivroDTO livroAtualizado) {
        Optional<Livro> livroExistent = livroRepository.findById(id);

        if (livroExistent.isEmpty()) {
            throw new CustomException("Livro não encontrado com o ID: " + id, null);
        }

        Livro livro = livroExistent.get();
        livro.setTitulo(livroAtualizado.getTitulo());
        livro.setAutor(livroAtualizado.getAutor());
        livro.setEditora(livroAtualizado.getEditora());
        livro.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
        livro.setDisponibilidade(livroAtualizado.isDisponibilidade());

        livro = livroRepository.save(livro);
        return livroMapper.livrtoDto(livro);
    }

    // Remover um livro pelo ID
    @Override
    public void removerLivro(Long id) {
        Optional<Livro> livro = livroRepository.findById(id);

        if (livro.isEmpty()) {
            throw new CustomException("Livro não encontrado com o ID: " + id, null);
        }

        livroRepository.deleteById(id);
    }

    // Consultar todos os livros
    @Override
    public List<LivroDTO> listarTodosLivros() {
        List<Livro> livros = livroRepository.findAll();
        return livros.stream()
                .map(livroMapper::livrtoDto)
                .collect(Collectors.toList());
    }

    // Consultar livros por disponibilidade
    @Override
    public List<LivroDTO> listarLivrosDisponiveis() {
        List<Livro> livrosDisponiveis = livroRepository.findByDisponibilidadeTrue();
        return livrosDisponiveis.stream()
                .map(livroMapper::livrtoDto)
                .collect(Collectors.toList());
    }

    // Consultar livro por ID
    @Override
    public LivroDTO buscarPorId(Long id) {
        Optional<Livro> livro = livroRepository.findById(id);

        if (livro.isEmpty()) {
            throw new CustomException("Livro não encontrado com o ID: " + id, null);
        }

        return livroMapper.livrtoDto(livro.get());
    }

    // Verificar se o livro pode ser emprestado
    @Override
    public void verificarDisponibilidade(Long id) {
        LivroDTO livroDTO = buscarPorId(id);

        if (!livroDTO.isDisponibilidade()) {
            throw new CustomException("O livro não está disponível para empréstimo.", null);
        }
    }
}
