package br.com.sinqia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterOrder {
    private Long id;
    private Register register;
    private Order order;
}
