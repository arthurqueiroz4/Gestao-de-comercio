package br.com.domain.dto;

import br.com.domain.entity.Mercado;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendaProdutoDTO {
    //Codigo de barras
    //quantidade
    //id_mercado
    private String codigoBarras;
    private Integer quantidade;
    private Integer id_mercado;
}
