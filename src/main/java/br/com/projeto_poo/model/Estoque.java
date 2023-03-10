package br.com.projeto_poo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "seq_estoque", sequenceName = "seq_estoque", allocationSize = 1, initialValue = 1)
public class Estoque{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_estoque")
	private Long idestoque;
	
	private String nomeProduto;
	
	private Double precoUnitario;
	
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_login", referencedColumnName = "idlogin")
	private Login id_login;

	public Long getId() {
		return idestoque;
	}

	public void setId(Long id) {
		this.idestoque = id;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public Double getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(Double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public Login getId_login() {
		return id_login;
	}

	public void setId_login(Login id_login) {
		this.id_login = id_login;
	}
	
	
}
