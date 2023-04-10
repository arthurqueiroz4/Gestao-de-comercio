package br.com.service;

import br.com.domain.dto.AtualizaQuantidadeDTO;
import br.com.domain.dto.EstoqueDTO;
import br.com.domain.dto.EstoqueRetornoDTO;
import br.com.domain.entity.Estoque;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface EstoqueService {
    Optional<EstoqueRetornoDTO> create(EstoqueDTO estoque);

    Optional<EstoqueDTO> updateEstoque(EstoqueDTO dto);
    List<Estoque> listarPeloNome(String login);

    void deleteByName(String login);
    //List<Estoque> updateQuantidade(AtualizaQuantidadeDTO quantidadeDTO);
}
