package br.com.sinqia.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
public class Funcionario {

    private Long id;
    private String nome;
    private BigDecimal salario;
    private Cargo cargo;
}
