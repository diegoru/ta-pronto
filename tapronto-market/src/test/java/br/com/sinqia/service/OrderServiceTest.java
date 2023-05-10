package br.com.sinqia.service;

import br.com.sinqia.dao.impl.OrderDAOImpl;
import br.com.sinqia.exceptions.OrderNotFoundException;
import br.com.sinqia.model.Order;
import br.com.sinqia.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl service;
    @Mock
    private OrderDAOImpl dao;

    @Test
    @DisplayName("Deve listar os pedidos")
    public void findAllTest() {
        List<Order> list = Arrays.asList(getOrder());
        when(dao.findAll()).thenReturn(list);
        assertEquals(list, service.findAll());
    }

    @Test
    @DisplayName("Deve buscar um pedido pelo id")
    public void findByIdTest(){
        Long id = 1L;
        Order order = getOrder();
        order.setId(id);
        when(dao.findById(anyLong())).thenReturn(Optional.of(order));
        assertEquals(order, service.findById(id));
    }

    @Test
    @DisplayName("Deve lancar erro pedido nao encontrado")
    public void exceptionNotFoundOrder() {
        Long id = 2L;
        when(dao.findById(anyLong())).thenReturn(Optional.empty());
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> service.findById(id));
        assertEquals("Order not found", exception.getMessage());
    }

    @Test
    @DisplayName("Deve salvar um pedido")
    public void saveOrderTest() {
        Order order = getOrder();
        service.save(order);
        verify(dao, times(1)).save(order);
    }

    @Test
    @DisplayName("Deve lancar erro pedido nulo")
    public void exceptionOrderNull() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.save(null));
        assertEquals("Unexpected error! No order to save", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar um pedido")
    public void updateOrderTest() {
        Order order = new Order();
        order.setId(1L);
        order.setCreatedAt(LocalDate.of(2023, 5, 9));
        when(dao.findById(anyLong())).thenReturn(Optional.of(order));
        Order orderUpdate = order;
        orderUpdate.setCreatedAt(LocalDate.of(2023,5,8));
        when(dao.save(any(Order.class))).thenReturn(orderUpdate);
        assertEquals(orderUpdate, service.update(1L, order));
    }

    private static Order getOrder() {
        Order order = new Order();
        order.setCreatedAt(LocalDate.of(2023, 5, 9));
        return order;
    }
}
