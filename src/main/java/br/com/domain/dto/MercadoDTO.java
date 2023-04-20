package br.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@Builder
public class MercadoDTO {
    @NotEmpty(message = "Campo login inválido.")
    private String login;
    private boolean admin;
}
