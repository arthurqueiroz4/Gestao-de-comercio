package br.com.service.impl;

import br.com.domain.dto.*;
import br.com.domain.entity.Estoque;
import br.com.domain.entity.Mercado;
import br.com.domain.entity.Produto;
import br.com.domain.entity.Vendas;
import br.com.domain.repository.EstoqueRepository;
import br.com.domain.repository.MercadoRepository;
import br.com.domain.repository.ProdutoRepository;
import br.com.domain.repository.VendasRepository;
import br.com.exception.BadRequestException;
import br.com.exception.NotFoundException;
import br.com.service.VendasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendasServiceImpl implements VendasService {

    @Autowired
    private MercadoRepository mercadoRepository;

    @Autowired
    private VendasRepository repositoryVendas;

    @Override
    public Vendas save(VendasDTO dto) {
        Vendas vendas = Vendas.builder()
                .date(dto.getDate())
                .descricao(dto.getDescricao())
                .codbarras(dto.getCodbarras())
                .precoUnitario(dto.getPrecoUnitario())
                .mercado(mercadoRepository.findById(dto.getId_mercado())
                        .orElseThrow(()-> new NotFoundException("Mercado não encontrado.")))
                .quantidade(dto.getQuantidade())
                .build();
        Vendas vendareturn = repositoryVendas.save(vendas);
        return vendareturn;
    }

    @Override
    public List<VendasRetornoDTO> mostrar(MercadoDTO dto) {

        List<Vendas> vendas = repositoryVendas.findByMercado(mercadoRepository
                .findByLogin(dto.getLogin())
                .orElseThrow(()-> new NotFoundException("Mercado não encontrado")).getId());
        List<VendasRetornoDTO> vendasRetornoDTO = new ArrayList<>();
        for (Vendas venda : vendas) {
            vendasRetornoDTO.add(VendasRetornoDTO.builder()
                    .date(venda.getDate())
                    .quantidade(venda.getQuantidade())
                    .precoUnitario(venda.getPrecoUnitario())
                    .descricao(venda.getDescricao())
                    .codbarras(venda.getCodbarras())
                    .build());
        }
        return vendasRetornoDTO;
    }
}
//{
//    list:[
//            {
//                codigoBarras:12312312,
//                quantidade:1
//            },
//            {
//              codigoBarras:41241234123,
//              quantidade:2
//            }
//         ],
//     id_mercado:312
//}
