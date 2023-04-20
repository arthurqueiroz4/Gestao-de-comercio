package br.com.domain.repository;

import br.com.domain.entity.Vendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface VendasRepository extends JpaRepository<Vendas, Integer> {

    @Query(value = "select e from Vendas e where e.mercado.id = ?1")
    List<Vendas> findByMercado(Integer id_mercado);
}
