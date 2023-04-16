package br.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class VendasDTO {
    private String codigoBarras;
    private Integer quantidade;
    private Integer id_mercado;
}
