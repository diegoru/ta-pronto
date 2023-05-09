package br.com.sinqia.service;

import br.com.sinqia.dao.impl.StockDAOImpl;
import br.com.sinqia.exceptions.CategoryNotFoundException;
import br.com.sinqia.exceptions.StockNotFoundException;
import br.com.sinqia.model.Category;
import br.com.sinqia.model.Stock;
import br.com.sinqia.service.impl.StockServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {
    @InjectMocks
    private StockServiceImpl service;

    @Mock
    private StockDAOImpl dao;

    @Test
    @DisplayName("Deve inserir um estoque")
    public void insertTest(){
        Stock stock = getStock();
        service.insert(stock);
        verify(dao, times(1)).insert(stock);
    }

    @Test
    @DisplayName("Deve lancar erro estoque nulo")
    public void shouldNotInsertAStockNull() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> service.insert(null));
        assertEquals("Unexpected error! No stock to insert", runtimeException.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar um estoque por id")
    public void updateTest() {
        Stock stock = getStock();
        stock.setId(1L);

        when(dao.findById(anyLong())).thenReturn(stock);

        stock.setDescription("Loja");

        service.update(stock);
        verify(dao, times(1)).update(stock);
    }

    @Test
    @DisplayName("Deve lancar estoque nao encontrado update")
    public void stockNotFoundUpdate() {
        Stock stock = getStock();
        stock.setId(1L);

        when(dao.findById(anyLong())).thenReturn(nullable(Stock.class));

        Stock stockUpdate = stock;
        stockUpdate.setDescription("Loja");

        StockNotFoundException stockNotFoundException = assertThrows(StockNotFoundException.class, () -> service.update(stockUpdate));
        assertEquals("Stock not found", stockNotFoundException.getMessage());
    }

    @Test
    @DisplayName("Deve deletar um estoque pelo id")
    public void deleteTest() {
        Stock stock = getStock();
        stock.setId(1L);
        when(dao.findById(anyLong())).thenReturn(stock);
        service.deleteById(stock.getId());
        verify(dao, times(1)).deleteById(stock.getId());
    }

    @Test
    @DisplayName("Deve lancar estoque nao encontrado pelo id ao deletar")
    public void stockNotFoundDelete() {
        when(dao.findById(anyLong())).thenReturn(null);
        StockNotFoundException stockNotFoundException = assertThrows(StockNotFoundException.class, () -> service.deleteById(1L));
        assertEquals("Stock not found", stockNotFoundException.getMessage());
    }

    @Test
    @DisplayName("Deve lancar estoque nao encontrado pelo id")
    public void stockNotFound() {
        when(dao.findById(anyLong())).thenReturn(null);
        StockNotFoundException stockNotFoundException = assertThrows(StockNotFoundException.class, () -> service.findById(1L));
        assertEquals("Stock not found", stockNotFoundException.getMessage());
    }

    @Test
    @DisplayName("Deve retornar lista de estoque")
    public void findAllTest() {
        List<Stock> list = Arrays.asList(getStock());
        when(dao.findAll()).thenReturn(list);
        assertEquals(list, service.findAll());
    }

    private static Stock getStock() {
        Stock stock = new Stock();
        stock.setDescription("Principal");
        return stock;
    }
}
