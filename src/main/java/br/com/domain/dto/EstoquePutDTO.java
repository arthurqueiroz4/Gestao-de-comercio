package br.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstoquePutDTO {
    @NotEmpty(message = "Campo codigoBarras inválida.")
    private String codigoBarras;
    @NotEmpty(message = "Campo nomeMercado inválida.")
    private String nomeMercado;
    private Integer quantidade;
    private  Double precoUnitario;
}

