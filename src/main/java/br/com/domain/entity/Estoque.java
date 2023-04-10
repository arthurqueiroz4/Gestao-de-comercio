package br.com.domain.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Builder
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @JoinColumn(name = "id_produto")
    @ManyToOne
    private Produto produto;

    @JoinColumn(name = "id_usuario")
    @OneToOne
    private Mercado mercado;

    @Column
    private Integer quantidade;

    @Column
    private Double precoUnitario;
}
