package br.com.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @JoinColumn(name = "id_produto")
    @ManyToOne
    private Produto produto;

    @JoinColumn(name = "id_mercado")
    @OneToOne
    private Mercado mercado;

    @Column
    private Integer quantidade;

    @Column
    private Double precoUnitario;
}
