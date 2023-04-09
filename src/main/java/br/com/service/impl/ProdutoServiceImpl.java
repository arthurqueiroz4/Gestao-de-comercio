package br.com.service.impl;

import br.com.domain.dto.ProdutoDTO;
import br.com.domain.entity.Produto;
import br.com.domain.entity.Usuario;
import br.com.domain.repository.ProdutoRepository;
import br.com.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoServiceImpl implements ProdutoService {
    @Autowired
    private ProdutoRepository repository;
    @Override
    @Transactional
    public Optional<ProdutoDTO> save(Produto produto) {
        boolean exists = repository.encontrarPeloCodigoBarra(produto.getCod_barras()).isPresent();
        if (!exists){
            repository.save(produto);
            ProdutoDTO produtoCadastrado = ProdutoDTO.builder()
                    .codig_barras(produto.getCod_barras())
                    .quantidade(produto.getQuantidade())
                    .preco(produto.getPrecoUnitario())
                    .decricao(produto.getDescricao())
                    .build();
            Optional<ProdutoDTO> produtoOptional = Optional.of(produtoCadastrado);
            return produtoOptional;
        }
        return Optional.empty();
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<Produto> list() {
        return repository.findAll();
    }

    @Override
    public List<Produto> getByNameLike(Produto produto) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of(produto, matcher);
        return repository.findAll(example);
    }

    @Override
    public Produto getById(Integer id) {
        return null;
    }

    @Override
    public Produto getByName(String nomeProduto) {
        return repository.encontrarPeloNome(nomeProduto)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @Override
    public Produto getByCode(String codigoBarra) {
        return repository.encontrarPeloCodigoBarra(codigoBarra)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }
}
