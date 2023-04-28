package br.com.sinqia.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Cargo {
    @EqualsAndHashCode.Include
    private Long id;
    private String descricao;
    private double aliquota;

    public Cargo(String descricao, double aliquota) {
        this.descricao = descricao;
        this.aliquota = aliquota;
    }
}
