package br.com.projeto_poo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize = 1,initialValue = 1,name = "seq_login",sequenceName = "seq_login") 
public class Login {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_login")
	private Long idlogin;
	
	private String usuario;
	
	private String senha;
	
	public Long getId() {
		return idlogin;
	}

	public void setId(Long id) {
		this.idlogin = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
}
