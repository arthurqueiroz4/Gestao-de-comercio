package br.com.service;

import br.com.domain.dto.UsuarioDTO;
import br.com.domain.entity.Mercado;

import java.util.List;
import java.util.Optional;

public interface MercadoService {
    Optional<UsuarioDTO> save(Mercado mercado);
    Mercado resetPassword(Mercado mercado);
    void delete(String login);
    List<Mercado> list();
    Mercado getByName(String name);
    Mercado getById(Integer id);
}
