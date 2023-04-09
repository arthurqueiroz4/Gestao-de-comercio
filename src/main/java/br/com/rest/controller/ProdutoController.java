package br.com.rest.controller;

import br.com.domain.dto.AtualizaNomeDTO;
import br.com.domain.dto.AtualizaPrecoDTO;
import br.com.domain.dto.ProdutoDTO;
import br.com.domain.dto.AtualizaQuantidadeDTO;
import br.com.domain.entity.Produto;
import br.com.service.impl.ProdutoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoServiceImpl service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO save(@RequestBody Produto produto){
        return service.save(produto).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Codigo de barras ja cadastrado"));
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoDTO> getByName(@RequestBody Produto produto){
        List<Produto> produtosEncontrados = service.getByNameLike(produto);
        List<ProdutoDTO> dtos = new ArrayList<>();
        for (Produto produtoEncontrado : produtosEncontrados) {
            dtos.add(
                    ProdutoDTO
                            .builder()
                            .decricao(produtoEncontrado.getDescricao())
                            .preco(produtoEncontrado.getPrecoUnitario())
                            .codig_barras((produtoEncontrado.getCod_barras()))
                            .quantidade(produtoEncontrado.getQuantidade())
                            .build()
            );
        }
        return dtos;
    }

    @PatchMapping
    @RequestMapping("quantidade")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateQuantidade(@RequestBody AtualizaQuantidadeDTO quantidade){
        Produto produtoEncontrado = service.getByCode(quantidade.getCodigoBarras());
        produtoEncontrado.setQuantidade(produtoEncontrado.getQuantidade()+quantidade.getNovaQuantidade());
        service.save(produtoEncontrado);
    }

    @PatchMapping
    @RequestMapping("nomeProduto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateNomeProduto(@RequestBody AtualizaNomeDTO atualizaNomeDTO){
        Produto produtoEncontrado = service.getByCode(atualizaNomeDTO.getCodigoBarras());
        produtoEncontrado.setDescricao(atualizaNomeDTO.getNovoNome());
        service.save(produtoEncontrado);
    }

    @PatchMapping
    @RequestMapping("preco")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePreco(@RequestBody AtualizaPrecoDTO atualizaPrecoDTO){
        Produto produtoEncontrado = service.getByCode(atualizaPrecoDTO.getCodigoBarras());
        produtoEncontrado.setPrecoUnitario(atualizaPrecoDTO.getNovoPreco());
        service.save(produtoEncontrado);
    }

}
