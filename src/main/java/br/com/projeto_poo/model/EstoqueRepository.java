package br.com.projeto_poo.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long>{
	//select u from Login u where u.usuario like %?1%
	@Query(value = "select u from Estoque u where u.nomeProduto like %?1% and u.id_login = ?2")
	List<Estoque> buscarProduto(String nomeProduto, Long id);
	
}
