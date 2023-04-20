package br.com.domain.dto;

import br.com.domain.entity.Mercado;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class VendaProdutoDTO {
    @NotEmpty(message = "Campo codigoBarras não pode ser nulo.")
    private String codigoBarras;
    @NotNull(message = "Campo quantidade não pode ser nulo.")
    private Integer quantidade;
    @NotNull(message = "Campo login não pode ser nulo.")
    private String login;
}
