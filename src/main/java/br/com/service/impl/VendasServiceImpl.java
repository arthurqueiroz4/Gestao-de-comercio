package br.com.service.impl;

import br.com.domain.dto.ProdutoListDTO;
import br.com.domain.dto.ProdutoQuantidadeBarrasDTO;
import br.com.domain.dto.VendasDTO;
import br.com.domain.entity.Estoque;
import br.com.domain.entity.Mercado;
import br.com.domain.entity.Produto;
import br.com.domain.entity.Vendas;
import br.com.domain.repository.EstoqueRepository;
import br.com.domain.repository.ProdutoRepository;
import br.com.domain.repository.VendasRepository;
import br.com.exception.BadRequestException;
import br.com.exception.NotFoundException;
import br.com.service.VendasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VendasServiceImpl implements VendasService {

    @Autowired
    private EstoqueRepository repositoryEstoque;
    @Autowired
    private ProdutoRepository repositoryProduto;
    @Autowired
    private VendasRepository repositoryVendas;

    @Override
    public Optional<Vendas> save(ProdutoListDTO dto) {
//        List<ProdutoQuantidadeBarrasDTO> list = dto.getList();
//
//        Vendas vendasReturn = Vendas.builder()
//                                    .date()
//                                    .mercado()
//                                    .produtoList()
//                                    .build();
        return null;
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
