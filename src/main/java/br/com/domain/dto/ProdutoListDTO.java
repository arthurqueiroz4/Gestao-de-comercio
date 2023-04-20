package br.com.domain.dto;

import br.com.domain.entity.Produto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
@Data
@Builder
public class ProdutoListDTO {
    @NotEmpty(message = "Campo list não pode ser vazio.")
    private List<ProdutoQuantidadeBarrasDTO> list;
    @NotNull(message = "Campo login não pode ser nulo.")
    private String login;
}
