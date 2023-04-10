package br.com.domain.repository;

import br.com.domain.entity.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstoqueRepository extends JpaRepository<Estoque, Integer> {

//    @Query(value = "select u from Estoque u where u.id_usuario = ?1")
//    List<Estoque> findAllByLogin(Integer id_login);

    @Query(value = "SELECT CASE WHEN COUNT(e) > 0 THEN TRUE ELSE FALSE END "+
            "FROM Estoque e "+
            "WHERE e.id_usuario = :id_mercado AND e.id_produto = :id_produto", nativeQuery = true)
    boolean existeEstoqueCadatrado(Integer id_mercado, Integer id_produto);

}
