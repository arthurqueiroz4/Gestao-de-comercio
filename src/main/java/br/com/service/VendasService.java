package br.com.service;

import br.com.domain.dto.MercadoDTO;
import br.com.domain.dto.ProdutoListDTO;
import br.com.domain.dto.VendasDTO;
import br.com.domain.dto.VendasRetornoDTO;
import br.com.domain.entity.Vendas;

import java.util.List;
import java.util.Optional;

public interface VendasService {
    Vendas save(VendasDTO dto);

    List<VendasRetornoDTO> mostrar(MercadoDTO dto);
}
