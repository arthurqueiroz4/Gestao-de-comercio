package br.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class EstoqueRetornoDTO {
    private String nomeMercado;
    private String nomeProduto;
    private Double precoUnitario;
    private Integer quantidade;
}
