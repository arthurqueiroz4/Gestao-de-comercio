package br.com.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoQuantidadeBarrasDTO {
    private String codigoBarras;
    private Integer quantidade;
}
