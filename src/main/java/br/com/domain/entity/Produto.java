package br.com.domain.entity;


import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    @NotEmpty(message = "Campo código de barras inválido.")
    private String cod_barras;
    @Column
    @NotEmpty(message = "Campo descricao inválido.")
    private String descricao;
}
