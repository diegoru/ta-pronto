package br.com.sinqia.dao;

import br.com.sinqia.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {

    List<Order> findAll();
    Optional<Order> findById(Long id);
    Order save(Order order);

    void deleteById(Long id);
}
