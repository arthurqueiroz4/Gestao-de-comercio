package br.com.domain.repository;

import br.com.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query(value = "select u from Usuario u where u.login = :login")
    Optional<Usuario> findByLogin(String login);
    @Query(value = "select u from Usuario u where u.cnpj = :cnpj")
    Optional<Usuario> findByCNPJ(String cnpj);

//    Optional<Usuario> findByLogin(String login);
}
