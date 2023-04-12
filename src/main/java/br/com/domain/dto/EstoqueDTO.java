package br.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@Builder
public class EstoqueDTO {
    @NotEmpty(message = "Campo codigoBarras inválida.")
    private String codigoBarras;
    @NotEmpty(message = "Campo nomeMercado inválida.")
    private String nomeMercado;
    @NotNull(message = "Campo quantidade inválida.")
    private Integer quantidade;
    @NotNull(message = "Campo precoUnitario inválida.")
    private  Double precoUnitario;
}
