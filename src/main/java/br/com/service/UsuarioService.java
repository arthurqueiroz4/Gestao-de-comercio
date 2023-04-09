package br.com.service;

import br.com.domain.dto.UsuarioDTO;
import br.com.domain.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Optional<UsuarioDTO> save(Usuario usuario);
    Usuario resetPassword(Usuario usuario);
    void delete(String login);
    List<Usuario> list();
    Usuario getByName(String name);
    Usuario getById(Integer id);
}
