package br.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
@Builder
@AllArgsConstructor
@Data
public class VendasRetornoDTO {
    private LocalDateTime date;

    private Integer quantidade;

    private Double precoUnitario;

    private String descricao;
    private String codbarras;
}
