package br.com.service.impl;

import br.com.domain.dto.UsuarioDTO;
import br.com.domain.entity.Usuario;
import br.com.domain.repository.UsuarioRepository;
import br.com.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;
    @Override
    @Transactional
    public Optional<UsuarioDTO> save(Usuario usuario){
        boolean exists = repository.findByLogin(usuario.getLogin()).isPresent();
        if (!exists){
            repository.save(usuario);
            return Optional.of(UsuarioDTO
                    .builder()
                    .admin(usuario.isAdmin())
                    .login(usuario.getLogin())
                    .build());
        }
        return Optional.empty();
    }

    @Override
    public Usuario resetPassword(Usuario usuario) {
        return repository.findByCNPJ(usuario.getCnpj()).map(user -> {
            user.setSenha(usuario.getSenha());
            return user;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(String login) {
       Usuario user = repository.findByLogin(login).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não cadastrado."));
       repository.deleteById(user.getId());
    }

    @Override
    public List<Usuario> list() {
        return repository.findAll();
    }

    @Override
    public Usuario getByName(String name) {
        return repository.findByLogin(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));
    }

    @Override
    public Usuario getById(Integer id) {
        return repository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }


}
