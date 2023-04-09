package br.com.domain.entity;

import javafx.beans.binding.DoubleExpression;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
