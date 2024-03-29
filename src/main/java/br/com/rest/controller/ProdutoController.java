package br.com.rest.controller;

import br.com.domain.dto.AtualizaNomeDTO;
import br.com.domain.dto.ProdutoDTO;
import br.com.domain.entity.Produto;
import br.com.exception.BadRequestException;
import br.com.service.impl.ProdutoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("**/api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoServiceImpl service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO save(@RequestBody @Valid Produto produto){
        return service.save(produto).orElseThrow(() -> new BadRequestException("Codigo de barras já cadastrado"));

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
                            .codig_barras((produtoEncontrado.getCod_barras()))
                            .build()
            );
        }
        return dtos;
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateNomeProduto(@RequestBody @Valid AtualizaNomeDTO atualizaNomeDTO){
        Produto produtoEncontrado = service.getByCode(atualizaNomeDTO.getCodigoBarras());
        produtoEncontrado.setDescricao(atualizaNomeDTO.getNovoNome());
        service.save(produtoEncontrado);
    }

    @GetMapping("/barras/{codigoBarras}")
    public ProdutoDTO buscarPeloCodigo (@PathVariable("codigoBarras") String codigoBarras ){
        Produto produto = service.getByCode(codigoBarras);
        return ProdutoDTO.builder()
                .codig_barras(produto.getCod_barras())
                .decricao(produto.getDescricao())
                .build();
    }

}
