package br.com.service;

import br.com.domain.dto.ProdutoDTO;
import br.com.domain.entity.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {

    Optional<ProdutoDTO> save(Produto produto);
    void delete(Integer id);
    List<Produto> list();
    List<Produto> getByNameLike(Produto produto);
    Produto getById(Integer id);
    Produto getByName(String nomeProduto);
    Produto getByCode(String codigoBarra);

}
