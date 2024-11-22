package com.biblioteca.biblioteca.domain.service;

import java.util.List;

import com.biblioteca.biblioteca.domain.dto.UsuarioDTO;

public interface IUsuarioService {

    UsuarioDTO cadastrarUsuario(UsuarioDTO usuario);

    UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioAtualizado);

    void removerUsuario(Long id);

    List<UsuarioDTO> listarTodosUsuarios();

    UsuarioDTO buscarPorId(Long id);

    UsuarioDTO buscarPorEmail(String email);
}
