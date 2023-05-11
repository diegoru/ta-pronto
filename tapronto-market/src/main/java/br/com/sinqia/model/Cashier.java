package br.com.sinqia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cashier {
    private Long id;
    private boolean open;
    private List<Register> registers;

    public Cashier(Long id, boolean open) {
        this.id = id;
        this.open = open;
    }
}
