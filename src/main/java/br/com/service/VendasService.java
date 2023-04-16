package br.com.service;

import br.com.domain.dto.ProdutoListDTO;
import br.com.domain.dto.VendasDTO;
import br.com.domain.entity.Vendas;

import java.util.Optional;

public interface VendasService {
    Optional<Vendas> save(ProdutoListDTO dto);
}
