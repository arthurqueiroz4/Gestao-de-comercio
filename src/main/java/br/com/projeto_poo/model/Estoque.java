
package br.com.projeto_poo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "seq_estoque", sequenceName = "seq_estoque", allocationSize = 1, initialValue = 1)
public class Estoque {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_estoque")
	private Long idEstoque;
	
	private String nomeProduto;
	
	private Double precoUnitario;
	
	private int quantidade;
	
	private Long id_login;

	public Long getIdEstoque() {
		return idEstoque;
	}

	public void setIdEstoque(Long idestoque) {
		this.idEstoque = idestoque;
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

	public Long getId_login() {
		return id_login;
	}

	public void setId_login(Long id_login) {
		this.id_login = id_login;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	
	
}