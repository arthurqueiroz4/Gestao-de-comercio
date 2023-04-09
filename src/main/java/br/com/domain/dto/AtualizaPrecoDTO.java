package br.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class AtualizaPrecoDTO {
    private Double novoPreco;
    private String codigoBarras;
}
