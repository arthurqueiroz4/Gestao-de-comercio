package br.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@Builder
public class AtualizaNomeDTO {
    @NotEmpty(message = "Campo código de barras inválido.")
    private String codigoBarras;
    @NotEmpty(message = "Campo novoNome inválido.")
    private String novoNome;
}
