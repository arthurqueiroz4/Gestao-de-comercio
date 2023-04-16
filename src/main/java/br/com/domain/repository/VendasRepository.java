package br.com.domain.repository;

import br.com.domain.entity.Vendas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendasRepository extends JpaRepository<Vendas, Integer> {
}
