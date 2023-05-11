package br.com.sinqia.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Register {

    private Long id;
    private LocalDateTime dateTime;
    private Cashier cashier;
    private BigDecimal openingBalance;
    private BigDecimal closedBalance;

    private List<RegisterOrder> registerOrders;

}
