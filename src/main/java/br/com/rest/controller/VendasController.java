package br.com.rest.controller;

import br.com.domain.dto.ProdutoListDTO;
import br.com.domain.dto.VendasDTO;
import br.com.domain.entity.Vendas;
import br.com.exception.BadRequestException;
import br.com.exception.NotFoundException;
import br.com.service.VendasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendasController {
    @Autowired
    private VendasService service;

    private List<VendasDTO> listVendas;

    @PostMapping
    public Vendas save(@RequestBody ProdutoListDTO dto){
        return service.save(dto).orElseThrow(()-> new BadRequestException("Não sei oq dizer"));
    }


}
