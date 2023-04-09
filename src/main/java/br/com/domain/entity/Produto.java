package br.com.domain.entity;


import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String cod_barras;
    @Column
    private Double precoUnitario;
    @Column
    private String descricao;
    @Column
    private Integer quantidade;
}
