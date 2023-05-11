package br.com.sinqia.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Long id;
    private Order order;
    private Product product;
    private Integer quantity;
}
