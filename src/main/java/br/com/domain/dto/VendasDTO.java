package br.com.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class VendasDTO {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)//yyyy-MM-dd
    private LocalDateTime date;
    @NotNull(message = "Campo quantidade não pode ser nulo.")
    private Integer quantidade;
    @NotNull(message = "Campo quantidade não pode ser nulo.")
    private Double precoUnitario;
    @NotEmpty(message = "Campo descricao não pode ser nulo.")
    private String descricao;
    @NotEmpty(message = "Campo codBarras não pode ser nulo.")
    private String codbarras;
    @NotNull(message = "Campo id_mercado não pode ser nulo.")
    private Integer id_mercado;
}
