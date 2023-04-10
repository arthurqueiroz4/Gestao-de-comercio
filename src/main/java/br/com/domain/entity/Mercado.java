package br.com.domain.entity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mercado")
@Data
public class Mercado {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "login")
    private String login;

    @Column(name = "senha")
    private String senha;

    @Column
    private boolean admin;

    @Column
    private String cnpj;

}
