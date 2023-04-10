package br.com.domain.repository;

import br.com.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    @Query(value = "select u from Produto u where u.descricao = :descricao")
    Optional<Produto> encontrarPeloNome(String descricao);
    @Query(value = "select u from Produto u where u.cod_barras = ?1")
    Optional<Produto> encontrarPeloCodigoBarra(String cod_barras);

}
