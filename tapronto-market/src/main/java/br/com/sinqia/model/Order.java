package br.com.sinqia.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private LocalDate createdAt;
    private BigDecimal amount;
    private List<OrderItem> items;
}
