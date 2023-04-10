package br.com.service.impl;

import br.com.domain.dto.MercadoDTO;
import br.com.domain.entity.Mercado;
import br.com.domain.repository.MercadoRepository;
import br.com.service.MercadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MercadoServiceImpl implements MercadoService {

    @Autowired
    private MercadoRepository repository;
    @Override
    @Transactional
    public Optional<MercadoDTO> save(Mercado mercado){
        boolean exists = repository.findByLogin(mercado.getLogin()).isPresent();
        if (!exists){
            repository.save(mercado);
            return Optional.of(MercadoDTO
                    .builder()
                    .admin(mercado.isAdmin())
                    .login(mercado.getLogin())
                    .build());
        }
        return Optional.empty();
    }

    @Override
    public Mercado resetPassword(Mercado mercado) {
        return repository.findByCNPJ(mercado.getCnpj()).map(user -> {
            user.setSenha(mercado.getSenha());
            return user;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(String login) {
       Mercado user = repository.findByLogin(login).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não cadastrado."));
       repository.deleteById(user.getId());
    }

    @Override
    public List<Mercado> list() {
        return repository.findAll();
    }

    @Override
    public Mercado getByName(String name) {
        return repository.findByLogin(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mercado não encontrado"));
    }

    @Override
    public Mercado getById(Integer id) {
        return repository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }


}
