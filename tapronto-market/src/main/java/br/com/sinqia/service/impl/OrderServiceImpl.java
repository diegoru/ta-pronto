package br.com.sinqia.service.impl;

import br.com.sinqia.dao.OrderDAO;
import br.com.sinqia.exceptions.OrderNotFoundException;
import br.com.sinqia.model.Order;
import br.com.sinqia.service.OrderService;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;

    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    @Override
    public Order findById(Long id) {
        Optional<Order> order = orderDAO.findById(id);
        order.orElseThrow(OrderNotFoundException::new);
        return order.get();
    }

    @Override
    public Order save(Order order) {
        if (order == null) throw new  RuntimeException("Unexpected error! No order to save");
        return orderDAO.save(order);
    }

    @Override
    public Order update(Long id, Order order) {
        if (order == null) throw new RuntimeException("Unexpected error! No order to update");
        Optional<Order> resulFindtOrder = orderDAO.findById(id);
        resulFindtOrder.orElseThrow(OrderNotFoundException::new);
        Order getOrder = resulFindtOrder.get();
        getOrder.setCreatedAt(order.getCreatedAt());
        getOrder.setAmount(order.getAmount());
        getOrder.setItems(order.getItems());
        return orderDAO.save(order);
    }
}
