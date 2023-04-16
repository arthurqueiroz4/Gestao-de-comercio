package br.com.service;

import br.com.domain.dto.*;
import br.com.domain.entity.Estoque;

import java.util.List;
import java.util.Optional;


public interface EstoqueService {
    Optional<EstoqueRetornoDTO> create(EstoqueDTO estoque);

    List<Estoque> listarPeloNome(String login);

    void deleteByName(String login);
    //List<Estoque> updateQuantidade(AtualizaQuantidadeDTO quantidadeDTO);

    Optional<EstoqueDTO> updateEstoque(EstoquePutDTO dto);

    void verificarVenda(VendaProdutoDTO dto);

    void vender(ProdutoListDTO dto);
}
