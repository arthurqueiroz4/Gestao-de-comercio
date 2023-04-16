package br.com.domain.dto;

import br.com.domain.entity.Produto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class ProdutoListDTO {
    private List<ProdutoQuantidadeBarrasDTO> list;
    private Integer id_mercado;
}
