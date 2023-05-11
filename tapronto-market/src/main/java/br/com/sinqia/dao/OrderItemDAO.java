package br.com.sinqia.dao;

import br.com.sinqia.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemDAO {

    Optional<OrderItem> findById(Long id);
    OrderItem save(OrderItem item);
    void deleteById(Long id);
    List<OrderItem> findItemByOrderId(Long idPedido);
}
