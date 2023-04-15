package br.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class CredenciaisDTO {
    @NotEmpty(message = "Campo login inválido.")
    private String login;
    @NotEmpty(message = "Campo senha inválido.")
    private String senha;
}
