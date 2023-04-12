package br.com.domain.entity;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "mercado")
@Data
public class Mercado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login")
    @NotEmpty(message = "Campo login inválido.")
    private String login;

    @Column(name = "senha")
    @NotEmpty(message = "Campo senha inválido.")
    private String senha;

    @Column
    private boolean admin;

    @Column@NotEmpty(message = "Campo CNPJ inválido.")
    @CNPJ(message = "Digite um CNPJ válido.")
    private String cnpj;

}
