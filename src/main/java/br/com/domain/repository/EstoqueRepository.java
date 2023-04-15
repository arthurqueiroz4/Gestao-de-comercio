package br.com.domain.repository;

import br.com.domain.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {

    @Query(value = "select u from Estoque u where u.mercado.id = ?1")
    List<Estoque> findAllByLogin(Integer id_login);

    @Query(value = "SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END "+
            "FROM Estoque e "+
            "WHERE e.id_mercado = :id_mercado AND e.id_produto = :id_produto", nativeQuery = true)
    boolean existeEstoqueCadatrado(Integer id_mercado, Integer id_produto);

    @Query(value = "SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END "+
            "FROM Estoque e "+
            "WHERE e.id_mercado = :id_mercado", nativeQuery = true)
    boolean existeEstoquePeloMercado(Integer id_mercado);

    @Query(value = "SELECT e FROM Estoque e WHERE e.mercado.id = ?1 " +
            "AND e.produto.id = ?2")
    Optional<Estoque> buscarEstoque(Integer id_mercado, Integer id_produto);

    @Query(value = "select e from Estoque e where e.mercado.login = ?1")
    List<Estoque> buscarPeloNomeMercado(String nomeMercado);

    @Query(value = "select e from Estoque e where e.mercado.login = ?1")
    Optional<Estoque> buscarPeloMercado(String login);
}
