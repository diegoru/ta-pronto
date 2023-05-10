package br.com.sinqia.service;

import br.com.sinqia.model.OrderItem;

public interface OrderItemService {
    OrderItem findById(Long id);
    OrderItem save(OrderItem orderItem);
    OrderItem update(Long id, OrderItem item);
    void deleteById(Long id);

}
