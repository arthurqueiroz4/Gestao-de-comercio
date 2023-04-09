package br.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class AtualizaQuantidadeDTO {
    private String codigoBarras;
    private Integer NovaQuantidade;
}
