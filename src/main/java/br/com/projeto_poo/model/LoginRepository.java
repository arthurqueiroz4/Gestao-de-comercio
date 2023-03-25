package br.com.projeto_poo.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long>{

	@Query(value = "select u from Login u where u.usuario like %?1%")
	List<Login> buscarUsuario(String usuario);

	@Query(value = "select u from Login u where u.cnpj like %?1%")
	List<Login> buscarCNPJ(String usuario);
	//@Query(value ="select u from Login u where cast(u.idLogin as TEXT) like ?1")
	//List<Login> findAllById(String id_login);
	
}