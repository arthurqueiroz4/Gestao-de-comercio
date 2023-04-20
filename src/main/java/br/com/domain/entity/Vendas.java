package br.com.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vendas")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vendas {
    @Id
    @GeneratedValue
    private Integer id;

    private LocalDateTime date;
    private Integer quantidade;
    private Double precoUnitario;
    private String descricao;
    private String codbarras;
    @ManyToOne
    private Mercado mercado;

}
