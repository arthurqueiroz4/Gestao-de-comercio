package br.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProdutoDTO {
    private String decricao;
    private Double preco;
    private String codig_barras;
    private Integer quantidade;
}
