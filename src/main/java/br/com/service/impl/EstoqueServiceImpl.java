package br.com.service.impl;

import br.com.domain.dto.EstoqueDTO;
import br.com.domain.dto.EstoquePutDTO;
import br.com.domain.dto.EstoqueRetornoDTO;
import br.com.domain.entity.Estoque;
import br.com.domain.entity.Mercado;
import br.com.domain.entity.Produto;
import br.com.domain.repository.EstoqueRepository;
import br.com.domain.repository.MercadoRepository;
import br.com.domain.repository.ProdutoRepository;
import br.com.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EstoqueServiceImpl implements EstoqueService {

    @Autowired
    EstoqueRepository repositoryEstoque;
    @Autowired
    MercadoRepository repositoryMercado;
    @Autowired
    ProdutoRepository repositoryProduto;

    @Override
    public Optional<EstoqueRetornoDTO> create(EstoqueDTO estoque) {

        Mercado mercado = repositoryMercado.findByLogin(estoque.getNomeMercado()).orElse(null);
        Produto produto = repositoryProduto.encontrarPeloCodigoBarra(estoque.getCodigoBarras()).orElse(null);
        System.out.println(mercado + " " + produto);
        boolean exists = repositoryEstoque.existeEstoqueCadatrado(mercado.getId(), produto.getId());
        if (!exists) {
            if (mercado != null && produto != null) {
                Estoque estoqueCadastrado = Estoque
                        .builder()
                        .mercado(mercado)
                        .produto(produto)
                        .precoUnitario(estoque.getPrecoUnitario())
                        .quantidade(estoque.getQuantidade())
                        .build();
                repositoryEstoque.save(estoqueCadastrado);
                return Optional.of(EstoqueRetornoDTO
                        .builder()
                        .precoUnitario(estoque.getPrecoUnitario())
                        .quantidade(estoque.getQuantidade())
                        .nomeProduto(produto.getDescricao())
                        .nomeMercado(estoque.getNomeMercado())
                        .build());
            }
        }

        return Optional.empty();

    }


    @Override
    public Optional<EstoqueDTO> updateEstoque(EstoquePutDTO dto) {
        Mercado mercado = repositoryMercado.findByLogin(dto.getNomeMercado()).orElse(null);
        Produto produto = repositoryProduto.encontrarPeloCodigoBarra(dto.getCodigoBarras()).orElse(null);
        if (mercado != null && produto != null) {
            Estoque estoque = repositoryEstoque.buscarEstoque(mercado.getId(), produto.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Produto " + produto.getDescricao() + " não encotrando no mercado " +
                                    mercado.getLogin()));
            if (dto.getQuantidade() != null){
                estoque.setQuantidade(estoque.getQuantidade() + dto.getQuantidade());
            }
            if (dto.getPrecoUnitario() != null){
                estoque.setPrecoUnitario(dto.getPrecoUnitario());
            }
            repositoryEstoque.save(estoque);
            return Optional.of(EstoqueDTO.builder()
                    .quantidade(estoque.getQuantidade())
                    .precoUnitario(estoque.getPrecoUnitario())
                    .codigoBarras(estoque.getProduto().getCod_barras())
                    .nomeMercado(estoque.getMercado().getLogin())
                    .build());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Estoque> listarPeloNome(String login) {
        return repositoryEstoque.buscarPeloNomeMercado(login);
    }

    @Override
    public void deleteByName(String login) {
        repositoryEstoque.deleteById(repositoryEstoque.buscarPeloMercado(login)
                        .map(c -> {
                            Integer id = c.getId();
                            return id;
                        })
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND
                , "O "+login+" não possui estoque."))
        );
    }

}

//    @Override
//    public List<Estoque> updateQuantidade(AtualizaQuantidadeDTO quantidadeDTO) {
//        return repositoryEstoque.findAllByLogin(quantidadeDTO.getId_Login());
//    }

