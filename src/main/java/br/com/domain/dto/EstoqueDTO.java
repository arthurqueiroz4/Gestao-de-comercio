package br.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EstoqueDTO {
    private String codigoBarras;
    private String nomeMercado;
    private Integer quantidade;
    private  Double precoUnitario;
}
