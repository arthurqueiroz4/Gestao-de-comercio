package br.com.rest.controller;

import br.com.domain.dto.AtualizaQuantidadeDTO;
import br.com.domain.dto.EstoqueDTO;
import br.com.domain.dto.EstoqueRetornoDTO;
import br.com.domain.entity.Estoque;
import br.com.service.EstoqueService;
import br.com.service.impl.EstoqueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {
    //adicionar produto(adicionar verificação), atualizar produto, apagar produto
    @Autowired
    private EstoqueServiceImpl service;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstoqueRetornoDTO created(@RequestBody EstoqueDTO estoque){
        return service.create(estoque)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já cadastrado"));
    }

//    @PatchMapping("quantidade")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public List<Estoque> updateQuantidade(@RequestBody AtualizaQuantidadeDTO quantidadeDTO){
//        return service.updateQuantidade(quantidadeDTO);
//    }

}
