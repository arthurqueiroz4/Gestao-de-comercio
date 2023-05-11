package br.com.rest.controller;

import br.com.domain.dto.MercadoDTO;
import br.com.domain.dto.ProdutoListDTO;
import br.com.domain.dto.VendasDTO;
import br.com.domain.dto.VendasRetornoDTO;
import br.com.domain.entity.Vendas;
import br.com.exception.BadRequestException;
import br.com.exception.NotFoundException;
import br.com.service.VendasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("**/api/vendas")
public class VendasController {
    @Autowired
    private VendasService service;

    @GetMapping
    public List<VendasRetornoDTO> mostrar(@PathParam("login") String login){
        System.out.println(login);
        return service.mostrar(login);
    }


}
