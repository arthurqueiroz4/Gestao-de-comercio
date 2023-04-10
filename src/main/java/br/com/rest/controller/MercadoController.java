package br.com.rest.controller;

import br.com.domain.dto.UsuarioDTO;
import br.com.domain.entity.Mercado;
import br.com.service.MercadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/usuarios")
public class MercadoController {

    @Autowired
    private MercadoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO save(@RequestBody Mercado mercado){
        return service.save(mercado).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já cadastrado."));
    }
    @GetMapping
    public UsuarioDTO getByName(@RequestBody Mercado user){
        Mercado mercadoEncontrado = service.getByName(user.getLogin());
        return UsuarioDTO.builder().login(user.getLogin()).admin(user.isAdmin()).build();
    }

    @DeleteMapping("{login}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("login") String login){
        System.out.println(login);
        service.delete(login);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@RequestBody Mercado mercado){
        service.resetPassword(mercado);
    }
}
