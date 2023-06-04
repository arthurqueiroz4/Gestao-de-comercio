package br.com.service.impl;

import br.com.domain.dto.*;
import br.com.domain.entity.Estoque;
import br.com.domain.entity.Mercado;
import br.com.domain.entity.Produto;
import br.com.domain.entity.Vendas;
import br.com.domain.repository.EstoqueRepository;
import br.com.domain.repository.MercadoRepository;
import br.com.domain.repository.ProdutoRepository;
import br.com.exception.BadRequestException;
import br.com.exception.NotFoundException;
import br.com.service.EstoqueService;
import br.com.service.VendasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueServiceImpl implements EstoqueService {

    @Autowired
    private EstoqueRepository repositoryEstoque;
    @Autowired
    private MercadoRepository repositoryMercado;
    @Autowired
    private ProdutoRepository repositoryProduto;
    @Autowired
    private VendasService vendasService;

    @Override
    public Optional<EstoqueRetornoDTO> create(EstoqueDTO estoque) {
        Mercado mercado = repositoryMercado.findByLogin(estoque.getNomeMercado()).orElse(null);
        Produto produto = repositoryProduto.encontrarPeloCodigoBarra(estoque.getCodigoBarras()).orElse(null);
        if (mercado==null && produto==null){
            throw new NotFoundException("Mercado e Produto não encontrados");
        }
        if (mercado==null){
            throw new NotFoundException("Mercado não encontrado.");
        }
        if (produto==null){
            throw new NotFoundException("Produto não encontrado.");
        }

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
    public void verificarVenda(VendaProdutoDTO dto) {
        Mercado mercado = repositoryMercado.findByLogin(dto.getLogin()).orElseThrow(
                ()-> new NotFoundException("Mercado não encontrado")
        );
        Produto produto = repositoryProduto.encontrarPeloCodigoBarra(dto.getCodigoBarras())
                .orElseThrow(()->new NotFoundException("Código de barras "+dto.getCodigoBarras()+" não encontrado."));
        Estoque estoque = repositoryEstoque.buscarEstoque(mercado.getId(), produto.getId())
                .orElseThrow(()->new NotFoundException("Código de barras "+dto.getCodigoBarras()+" não cadastrado no Estoque."));
        if (estoque.getQuantidade() == 0){
            throw new BadRequestException("Estoque de "+produto.getDescricao()+" zerado.");
        }
        if ((estoque.getQuantidade() - dto.getQuantidade()) < 0){
            throw new BadRequestException("Não há "+dto.getQuantidade()+" produtos disponíveis no estoque. Existe "+estoque.getQuantidade()+" no Estoque.");
        }
//        estoque.setQuantidade(estoque.getQuantidade() - dto.getQuantidade());
//        repositoryEstoque.save(estoque);

    }

    @Override
    public void vender(ProdutoListDTO dto) {
        Mercado mercado = repositoryMercado.findByLogin(dto.getLogin()).orElseThrow(
                ()-> new NotFoundException("Mercado não encontrado")
        );
        List<ProdutoQuantidadeBarrasDTO> list = dto.getList();
        for (ProdutoQuantidadeBarrasDTO produtoQuantidadeBarrasDTO : list) {
            Produto produto = repositoryProduto.encontrarPeloCodigoBarra(produtoQuantidadeBarrasDTO.getCodigoBarras())
                    .orElse(null);
            Estoque estoque = repositoryEstoque.buscarEstoque(mercado.getId(), produto.getId())
                    .orElse(null);
            estoque.setQuantidade(estoque.getQuantidade() - produtoQuantidadeBarrasDTO.getQuantidade());
            repositoryEstoque.save(estoque);
            VendasDTO vendasDTO = VendasDTO.builder()
                    .date(LocalDateTime.now())
                    .descricao(produto.getDescricao())
                    .codbarras(produto.getCod_barras())
                    .precoUnitario(estoque.getPrecoUnitario())
                    .login(mercado.getLogin())
                    .quantidade(produtoQuantidadeBarrasDTO.getQuantidade())
                    .build();
            vendasService.save(vendasDTO);
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


    public EstoqueRetornoListDTO getByCode(String codigoBarras, String mercado) {
        Estoque estoque = repositoryEstoque.buscarProdutoNoEstoque(codigoBarras, mercado)
                                .orElseThrow(() -> new NotFoundException("Produto não cadastrado no seu Estoque."));
        
        return EstoqueRetornoListDTO.builder()
                                    .codigoBarras(estoque.getProduto().getCod_barras())
                                    .quantidade(estoque.getQuantidade())
                                    .nomeProduto(estoque.getProduto().getDescricao())
                                    .precoUnitario(estoque.getPrecoUnitario())
                                    .build();
    }

}

//    @Override
//    public List<Estoque> updateQuantidade(AtualizaQuantidadeDTO quantidadeDTO) {
//        return repositoryEstoque.findAllByLogin(quantidadeDTO.getId_Login());
//    }

