package br.com.sinqia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PagamentoRealizado {

    private Funcionario funcionario;
    private BigDecimal valorPago;

}
