package br.com.rest.controller;

import br.com.domain.dto.*;
import br.com.domain.entity.Estoque;
import br.com.exception.BadRequestException;
import br.com.exception.NotFoundException;
import br.com.service.ProdutoService;
import br.com.service.impl.EstoqueServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("**/api/estoque")
public class EstoqueController {

    @Autowired
    private EstoqueServiceImpl service;
    @Autowired
    private ProdutoService produtoService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstoqueRetornoDTO created(@RequestBody @Valid EstoqueDTO estoque){
        return service.create(estoque)
                .orElseThrow(()-> new BadRequestException("Estoque já cadastrado"));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public EstoqueDTO updateEstoque(@RequestBody @Valid EstoquePutDTO dto){
        if (dto.getQuantidade()==null && dto.getPrecoUnitario()==null){
            throw new NotFoundException("O campo quantidade e precoUnitario não podem ser ambos null.");
        }
        return service.updateEstoque(dto).get();
    }

    @GetMapping("/verificar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verificar(@RequestParam("codigoBarras") String codigoBarras,
                        @RequestParam("quantidade") Integer quantidade,
                        @RequestParam("login") String login){
        
        service.verificarVenda(VendaProdutoDTO.builder()
                                .codigoBarras(codigoBarras)
                                .login(login)
                                .quantidade(quantidade)
                                .build());
    }

    @PostMapping("/vender")
    @ResponseStatus(HttpStatus.OK)
    public void vender(@RequestBody @Valid ProdutoListDTO dto){
        service.vender(dto);
    }

    @GetMapping
    public List<EstoqueRetornoListDTO> listByMercado(@PathParam("login") String login){
        List<Estoque> listEstoque = service.listarPeloNome(login);
        List<EstoqueRetornoListDTO> dtoRetorno = new ArrayList<>();
        for (Estoque estoque : listEstoque) {
            dtoRetorno.add(
                    EstoqueRetornoListDTO
                            .builder()
                            .nomeProduto(estoque.getProduto().getDescricao())
                            .precoUnitario(estoque.getPrecoUnitario())
                            .quantidade(estoque.getQuantidade())
                            .codigoBarras(produtoService.getByName(
                                    estoque.getProduto().getDescricao()
                            ).getCod_barras())
                            .build()
            );
        }
        return dtoRetorno;
    }

    @DeleteMapping
    public void deleteByName(@RequestBody @Valid DeleteEstoqueDTO dto){
        System.out.println(dto.getLogin());
        service.deleteByName(dto.getLogin());
    }

    @GetMapping("/produto")
    public EstoqueRetornoListDTO getByProduto(@RequestParam("codigo") String codigoBarras,
                                            @RequestParam("mercado") String mercado){
        return service.getByCode(codigoBarras, mercado);
    }
}