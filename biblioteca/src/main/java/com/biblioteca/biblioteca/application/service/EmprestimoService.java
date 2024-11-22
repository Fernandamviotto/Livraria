package com.biblioteca.biblioteca.application.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.biblioteca.application.Mappers;
import com.biblioteca.biblioteca.domain.dto.EmprestimoDTO;
import com.biblioteca.biblioteca.domain.entity.Emprestimo;
import com.biblioteca.biblioteca.domain.entity.Livro;
import com.biblioteca.biblioteca.domain.entity.Usuario;
import com.biblioteca.biblioteca.domain.repository.IEmprestimoRepository;
import com.biblioteca.biblioteca.domain.repository.ILivroRepository;
import com.biblioteca.biblioteca.domain.repository.IUsuarioRepository;
import com.biblioteca.biblioteca.domain.service.IEmprestimoService;
import com.biblioteca.biblioteca.shared.CustomException;
import com.biblioteca.biblioteca.shared.StatusEmprestimo;

@Service
public class EmprestimoService implements IEmprestimoService {

    @Autowired
    private IEmprestimoRepository emprestimoRepository;
    private ILivroRepository livroRepository;
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private Mappers emprestimoMapper;

    @Override
    public EmprestimoDTO cadastrarEmprestimo(EmprestimoDTO emprestimoDTO) {
        Usuario usuario = usuarioRepository.findById(emprestimoDTO.getUsuarioDTO().getId())
                .orElseThrow(() -> new CustomException("Usuário não encontrado", null));

        Livro livro = livroRepository.findById(emprestimoDTO.getLivroDTO().getId())
                .orElseThrow(() -> new CustomException("Livro não encontrado", null));

        // Verificar se o usuário já atingiu o limite de empréstimos
        long emprestimosAtivos = emprestimoRepository.countByUsuario_IdAndStatus(usuario.getId(),
                StatusEmprestimo.ATIVO);
        if (emprestimosAtivos >= 5) {
            throw new CustomException("Usuário atingiu o limite de 5 livros simultaneamente.", null);
        }

        // Verificar se o livro está disponível
        boolean livroDisponivel = !emprestimoRepository.existsByLivro_IdAndStatus(livro.getId(),
                StatusEmprestimo.ATIVO);
        if (!livroDisponivel) {
            throw new CustomException("Livro não está disponível para empréstimo.", null);
        }

        // Criar novo empréstimo
        Emprestimo emprestimo = Emprestimo.builder()
                .usuario(usuario)
                .livro(livro)
                .dataEmprestimo(LocalDate.now())
                .dataDevolucaoPrevista(LocalDate.now().plusDays(14))
                .status(StatusEmprestimo.ATIVO)
                .build();

        emprestimoRepository.save(emprestimo);
        return emprestimoMapper.EmprestimotoDto(emprestimo);
    }

    @Override
    public EmprestimoDTO registrarDevolucao(Long id, EmprestimoDTO devolucaoDTO) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new CustomException("Empréstimo não encontrado", null));

        LocalDate dataDevolucao = devolucaoDTO.getDataDevolucaoRealizada();
        if (dataDevolucao.isAfter(emprestimo.getDataDevolucaoPrevista())) {
            long diasAtraso = ChronoUnit.DAYS.between(emprestimo.getDataDevolucaoPrevista(), dataDevolucao);
            emprestimo.setMultaAplicada(diasAtraso * 1.0); // R$ 1,00 por dia
            emprestimo.setStatus(StatusEmprestimo.EM_ATRASO);
        } else {
            emprestimo.setMultaAplicada(0.0);
            emprestimo.setStatus(StatusEmprestimo.CONCLUIDO);
        }

        emprestimo.setDataDevolucaoRealizada(dataDevolucao);
        emprestimoRepository.save(emprestimo);

        return emprestimoMapper.EmprestimotoDto(emprestimo);
    }

    @Override
    public List<EmprestimoDTO> consultarHistoricoPorUsuario(Long id) {
        // Busca todos os empréstimos do usuário pelo ID
        return emprestimoRepository.findByUsuario_Id(id).stream()
                .map(emprestimoMapper::EmprestimotoDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmprestimoDTO> consultarHistoricoPorLivro(Long id) {
        // Busca todos os empréstimos do livro pelo ID
        return emprestimoRepository.findByLivro_Id(id).stream()
                .map(emprestimoMapper::EmprestimotoDto)
                .collect(Collectors.toList());
    }

    // @Override
    // public List<EmprestimoDTO> consultarEmprestimosEmAtraso() {
    // throw new UnsupportedOperationException("Unimplemented method
    // 'consultarEmprestimosEmAtraso'");
    // }
}
