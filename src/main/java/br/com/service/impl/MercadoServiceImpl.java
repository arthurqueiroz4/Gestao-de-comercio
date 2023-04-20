package br.com.service.impl;

import br.com.domain.dto.MercadoDTO;
import br.com.domain.entity.Mercado;
import br.com.domain.repository.EstoqueRepository;
import br.com.domain.repository.MercadoRepository;
import br.com.exception.DeleteInvalidoException;
import br.com.exception.NotFoundException;
import br.com.exception.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class MercadoServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MercadoRepository repository;

    @Autowired
    private EstoqueServiceImpl serviceEstoque;

    @Autowired
    private EstoqueRepository repositoryEstoque;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Mercado mercado = repository.findByLogin(username)
                .orElseThrow(()-> new NotFoundException("Usuário não cadastrado"));
        String[] roles = mercado.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};
        UserDetails user = User
                .builder()
                .username(mercado.getLogin())
                .password(mercado.getSenha())
                .roles(roles)
                .build();
        System.out.println(user);
        return user;
    }

    public UserDetails autenticar(Mercado mercado){
        UserDetails mercadoUser = loadUserByUsername(mercado.getLogin());
        String senhaDecript = mercado.getSenha();
        String senhaCript = mercadoUser.getPassword();
        boolean senhasBatem = passwordEncoder.matches(senhaDecript, senhaCript);
        if (senhasBatem){
            return mercadoUser;
        }
        throw new SenhaInvalidaException();
    }


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


    public Mercado resetPassword(Mercado mercado) {
        Mercado mercadoEncontrado = repository.findByLogin(mercado.getLogin())
                .orElseThrow(()-> new NotFoundException("Mercado não cadastrado."));
        mercadoEncontrado.setSenha(passwordEncoder.encode(mercado.getSenha()));
        repository.save(mercadoEncontrado);
        return mercadoEncontrado;
    }

    public void delete(String login) {
       Mercado user = repository.findByLogin(login).orElseThrow(()-> new NotFoundException("Mercado não cadastrado."));
       repository.deleteById(user.getId());
    }

    public Mercado getByName(String name) {
        return repository.findByLogin(name).orElseThrow(()-> new NotFoundException("Mercado não cadastrado."));
    }
//
//    @Override
//    public Mercado getById(Integer id) {
//        return repository.findById(id).orElseThrow(()-> new NotFoundException("Mercado não cadastrado."));
//    }

    public void delete(Mercado mercado){
        if (repositoryEstoque.existeEstoquePeloMercado(mercado.getId())){
            throw new DeleteInvalidoException("Esse mercado possui estoque cadastrado, não é possível deletar Mercad com estoque.");
        }
        repository.deleteById(mercado.getId());
    }

}
