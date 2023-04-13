package br.com.rest.controller;

import br.com.domain.dto.MercadoDTO;
import br.com.domain.entity.Mercado;
import br.com.exception.BadRequestException;
import br.com.exception.NotFoundException;
import br.com.service.MercadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class MercadoController {

    @Autowired
    private MercadoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MercadoDTO save(@RequestBody @Valid Mercado mercado){
        return service.save(mercado).orElseThrow(()-> new BadRequestException("Mercado já cadastrado."));
    }
    @GetMapping
    public MercadoDTO getByName(@RequestBody @Valid MercadoDTO user){
        Mercado mercadoEncontrado = service.getByName(user.getLogin());
        return MercadoDTO.builder().login(user.getLogin()).admin(user.isAdmin()).build();
    }

    @DeleteMapping("{login}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("login") String login){
        service.delete(login);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@RequestBody @Valid Mercado mercado){
        service.resetPassword(mercado);
    }


}
