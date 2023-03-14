package br.com.projeto_poo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(allocationSize = 1,initialValue = 1,name = "seq_login",sequenceName = "seq_login") 
public class Login {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_login")
	private Long idLogin;
	
	private String usuario;
	
	private String senha;

	private String cnpj;
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Long getIdLogin() {
		return idLogin;
	}

	public void setIdLogin(Long idlogin) {
		this.idLogin = idlogin;
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
