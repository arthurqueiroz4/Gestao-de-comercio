package br.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AtualizaNomeDTO {
    private String codigoBarras;
    private String novoNome;
}
