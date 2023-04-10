package br.com.domain.repository;

import br.com.domain.entity.Mercado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MercadoRepository extends JpaRepository<Mercado, Integer> {
    @Query(value = "select u from Mercado u where u.login = :login")
    Optional<Mercado> findByLogin(String login);
    @Query(value = "select u from Mercado u where u.cnpj = :cnpj")
    Optional<Mercado> findByCNPJ(String cnpj);

//    Optional<Mercado> findByLogin(String login);
}
