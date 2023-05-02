package br.com.sinqia.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
public class Cargo {
    @EqualsAndHashCode.Include
    private Long id;
    private String descricao;
    private double aliquota;
}
