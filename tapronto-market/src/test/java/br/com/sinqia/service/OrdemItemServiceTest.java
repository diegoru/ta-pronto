package br.com.sinqia.service;

import br.com.sinqia.dao.OrderItemDAO;
import br.com.sinqia.dao.impl.OrderItemDAOImpl;
import br.com.sinqia.exceptions.OrderItemNotFoundException;
import br.com.sinqia.model.Category;
import br.com.sinqia.model.Order;
import br.com.sinqia.model.OrderItem;
import br.com.sinqia.model.Product;
import br.com.sinqia.service.impl.OrderItemServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrdemItemServiceTest {

    @InjectMocks
    private OrderItemServiceImpl itemService;

    @Mock
    private OrderItemDAOImpl itemDAO;


    @Test
    @DisplayName("Deve listar os itens dos pedidos")
    public void findAllTest() {
        OrderItem item = getOrderItem();
        item.setId(1L);
        List<OrderItem> listItems = Arrays.asList(item);
        when(itemDAO.findAll()).thenReturn(listItems);
        assertEquals(listItems, itemService.findAll());
    }

    @Test
    @DisplayName("Deve retornar um item pedido por id")
    public void findByIdTest() {
        Long id = 1L;
        OrderItem orderItem = getOrderItem();
        orderItem.setId(id);
        when(itemDAO.findById(anyLong())).thenReturn(Optional.of(orderItem));
        assertEquals(orderItem, itemService.findById(id));
    }

    @Test
    @DisplayName("Deve lancar erro item pedido nao encontrado")
    public void orderItemNotFoundTest() {
        Long id = 2L;
        when(itemDAO.findById(anyLong())).thenReturn(Optional.empty());
        OrderItemNotFoundException exception = assertThrows(OrderItemNotFoundException.class, () -> itemService.findById(id));
        assertEquals("Order item not found", exception.getMessage());
    }

    @Test
    @DisplayName("Deve salvar um item pedido")
    public void saveTest() {
        OrderItem orderItem = getOrderItem();
        when(itemDAO.save(any(OrderItem.class))).thenReturn(orderItem);
        assertEquals(orderItem, itemService.save(orderItem));
    }

    @Test
    @DisplayName("Deve lancar erro ao tentgar salvar um item pedido nulo")
    public void exceptionSaveTest() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> itemService.save(null));
        assertEquals("Unexpected error! No order item to save", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar um item pedido")
    public void updateTest() {
        Long id = 1L;
        OrderItem orderItem = getOrderItem();
        orderItem.setId(id);
        when(itemDAO.findById(anyLong())).thenReturn(Optional.of(orderItem));
        OrderItem orderItemUpdate = orderItem;
        orderItemUpdate.setQuantity(2);
        when(itemDAO.save(any(OrderItem.class))).thenReturn(orderItemUpdate);
        assertEquals(orderItemUpdate, itemService.update(id, orderItem));
    }

    @Test
    @DisplayName("Deve lancar erro item pedido nao encontrado  ao atualizar")
    public void exceptionNotFoundUpdate() {
        Long id = 1L;
        OrderItem orderItem = getOrderItem();
        orderItem.setId(id);
        when(itemDAO.findById(anyLong())).thenReturn(Optional.empty());
        OrderItemNotFoundException exception = assertThrows(OrderItemNotFoundException.class, () -> itemService.update(id, orderItem));
        assertEquals("Order item not found", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lancar erro item pedido nulo ao atualizar")
    public void exceptionNullUpdate() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> itemService.update(1L, null));
        assertEquals("Unexpected error! No order item to update", exception.getMessage());
    }

    @Test
    @DisplayName("Deve remover um item pedido")
    public void deleteTest() {
        Long id = 1L;
        OrderItem orderItem = getOrderItem();
        when(itemDAO.findById(id)).thenReturn(Optional.of(orderItem));
        itemService.deleteById(id);
        verify(itemDAO, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deve lancar erro item pedido ao encontrado ao tentar remover")
    public void exceptionNotFoundDelete() {
        Long id = 1L;
        OrderItem orderItem = getOrderItem();
        when(itemDAO.findById(id)).thenReturn(Optional.empty());
        OrderItemNotFoundException exception = assertThrows(OrderItemNotFoundException.class, () -> itemService.deleteById(id));
        assertEquals("Order item not found", exception.getMessage());
    }

    private static OrderItem getOrderItem(){
        Order order = new Order();
        order.setId(1L);
        order.setCreatedAt(LocalDate.of(2023, 4, 10));

        Category category = new Category(1L, "Matinais");

        Product product = new Product(1L, "Bolacha Trakinas", new BigDecimal("4.9"), category, 10);

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(1);
        return item;
    }
}
