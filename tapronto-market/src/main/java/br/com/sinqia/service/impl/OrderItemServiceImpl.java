package br.com.sinqia.service.impl;

import br.com.sinqia.dao.OrderItemDAO;
import br.com.sinqia.exceptions.OrderItemNotFoundException;
import br.com.sinqia.model.OrderItem;
import br.com.sinqia.service.OrderItemService;

import java.util.Optional;

public class OrderItemServiceImpl implements OrderItemService {

    private OrderItemDAO dao;

    @Override
    public OrderItem findById(Long id) {
        Optional<OrderItem> orderItem = dao.findById(id);
        orderItem.orElseThrow(OrderItemNotFoundException::new);
        return orderItem.get();
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        if (orderItem == null) throw new RuntimeException("Unexpected error! No order item to save");
        return dao.save(orderItem);
    }

    @Override
    public OrderItem update(Long id, OrderItem item) {
        if (item == null) throw new RuntimeException("Unexpected error! No order item to update");
        Optional<OrderItem> orderItem = dao.findById(id);
        orderItem.orElseThrow(OrderItemNotFoundException::new);
        OrderItem getOrdemItem = orderItem.get();
        getOrdemItem.setOrder(item.getOrder());
        getOrdemItem.setProduct(item.getProduct());
        getOrdemItem.setQuantity(item.getQuantity());
        return dao.save(getOrdemItem);
    }

    @Override
    public void deleteById(Long id) {
        Optional<OrderItem> orderItem = dao.findById(id);
        orderItem.orElseThrow(OrderItemNotFoundException::new);
        dao.deleteById(id);
    }
}
