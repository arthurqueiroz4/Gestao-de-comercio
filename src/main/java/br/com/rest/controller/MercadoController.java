package br.com.rest.controller;

import br.com.JWT.JwtService;
import br.com.domain.dto.CredenciaisDTO;
import br.com.domain.dto.MercadoDTO;
import br.com.domain.dto.TokenDTO;
import br.com.domain.entity.Mercado;
import br.com.exception.BadRequestException;
import br.com.exception.NotFoundException;
import br.com.exception.SenhaInvalidaException;
import br.com.service.impl.MercadoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class MercadoController {

    @Autowired
    private MercadoServiceImpl service;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MercadoDTO save(@RequestBody @Valid Mercado mercado){

        String senhaEncrip = passwordEncoder.encode(mercado.getSenha());
        mercado.setSenha(senhaEncrip);
        return service.save(mercado).orElseThrow(()-> new BadRequestException("Mercado já cadastrado."));
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody Mercado mercado){
        Mercado mercadoEncontrado = service.getByName(mercado.getLogin());
        service.delete(mercadoEncontrado);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody @Valid CredenciaisDTO credenciais){
        try{
            Mercado mercado = Mercado.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            UserDetails user = service.autenticar(mercado);
            String token = jwtService.gerarToken(mercado);
            return new TokenDTO(mercado.getLogin(), token);
        }catch (SenhaInvalidaException e){
            throw e;
        }catch (NotFoundException e){
            throw new NotFoundException("Usuário não encontrado.");
        }
    }

    @GetMapping
    public MercadoDTO getByName(@RequestBody @Valid MercadoDTO user){
        Mercado mercadoEncontrado = service.getByName(user.getLogin());
        return MercadoDTO.builder().login(user.getLogin()).admin(user.isAdmin()).build();
    }


    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@RequestBody @Valid Mercado mercado){
        service.resetPassword(mercado);
    }


}
