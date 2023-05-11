package br.com.sinqia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cashier {
    private Long id;
    private boolean open;

}
