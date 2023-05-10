package br.com.sinqia.service;

import br.com.sinqia.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> findAll();
    Order findById(Long id);
    Order save(Order order);
    Order update(Long id, Order order);
}
