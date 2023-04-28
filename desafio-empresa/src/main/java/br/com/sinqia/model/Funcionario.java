package br.com.sinqia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Funcionario {

    private Long id;
    private String nome;
    private BigDecimal salario;
    private Cargo cargo;

    public Funcionario(String nome, BigDecimal salario, Cargo cargo) {
        this.nome = nome;
        this.salario = salario;
        this.cargo = cargo;
    }
}
