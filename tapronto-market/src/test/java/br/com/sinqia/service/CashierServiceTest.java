package br.com.sinqia.service;

import br.com.sinqia.dao.impl.CashierDAOImpl;
import br.com.sinqia.exceptions.CashierNotFoundException;
import br.com.sinqia.model.Cashier;
import br.com.sinqia.service.impl.CashierServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CashierServiceTest {

    @InjectMocks
    private CashierServiceImpl cashierService;
    @Mock
    private CashierDAOImpl cashierDAO;

    @Test
    @DisplayName("Deve retornar lista de caixas")
    public void findAllTest() {
        Cashier cashier = createCashier();
        cashier.setId(1L);
        List<Cashier> cashierList = Arrays.asList(cashier);
        when(cashierDAO.findAll()).thenReturn(cashierList);
        assertEquals(cashierList, cashierService.findAll());
    }

    @Test
    @DisplayName("Deve retornar um caixa pelo id")
    public void findByIdTest() {
        Long id = 1L;
        Cashier cashier = createCashier();
        cashier.setId(id);

        when(cashierDAO.findById(anyLong())).thenReturn(Optional.of(cashier));
        assertEquals(cashier, cashierService.findById(1L));
    }

    @Test
    @DisplayName("Deve lancar erro caixa não encontrado")
    public void exceptionCashierNotfoundById() {
        Long id = 1L;
        when(cashierDAO.findById(anyLong())).thenReturn(Optional.empty());
        CashierNotFoundException exception = assertThrows(CashierNotFoundException.class, () -> cashierService.findById(id));
        assertEquals("Cashier not found", exception.getMessage());
    }

    @Test
    @DisplayName("Deve salvar um caixa")
    public void saveTest() {
        Cashier cashier = createCashier();
        when(cashierDAO.save(any(Cashier.class))).thenReturn(cashier);
        assertEquals(cashier, cashierService.save(cashier));
    }

    @Test
    @DisplayName("Deve lancar erro caixa nulo")
    public void exceptionCashierNull() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> cashierService.save(null));
        assertEquals("Unexpected error! No cashier to save", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar caixa")
    public void updateTest() {
        Cashier cashier = createCashier();
        cashier.setId(1L);
        when(cashierDAO.findById(anyLong())).thenReturn(Optional.of(cashier));
        Cashier cashierUpdate = cashier;
        cashierUpdate.setOpen(true);
        when(cashierDAO.save(any(Cashier.class))).thenReturn(cashierUpdate);
        assertEquals(cashierUpdate, cashierService.update(1L, cashierUpdate));
    }

    @Test
    @DisplayName("Deve lancar erro caixa nulo")
    public void exceptionCashierNullToUpdate() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> cashierService.update(1L, null));
        assertEquals("Unexpected error! No cashier to update", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lancar erro caixa nao encontrado ao atualizar")
    public void exceptionCashierNotFound() {
        Long id = 1L;
        Cashier cashier = createCashier();
        cashier.setId(id);
        when(cashierDAO.findById(anyLong())).thenReturn(Optional.empty());
        CashierNotFoundException exception = assertThrows(CashierNotFoundException.class, () -> cashierService.update(1L, cashier));
        assertEquals("Cashier not found", exception.getMessage());
    }

    @Test
    @DisplayName("Deve remover um caixa pelo id")
    public void deleteTest() {
        Long id = 1L;
        Cashier cashier = createCashier();
        cashier.setId(id);
        when(cashierDAO.findById(id)).thenReturn(Optional.of(cashier));
        cashierService.deleteById(1L);
        verify(cashierDAO, times(1)).deletebyId(id);
    }

    @Test
    @DisplayName("Deve lancar erro caixa nao encontrado ao tentar remover por id")
    public void expetionCashierNotFoundDelete() {
        Long id = 1L;
        when(cashierDAO.findById(anyLong())).thenReturn(Optional.empty());
        CashierNotFoundException exception = assertThrows(CashierNotFoundException.class, () -> cashierService.deleteById(id));
        assertEquals("Cashier not found", exception.getMessage());

    }

    private Cashier createCashier() {
        Cashier cashier = new Cashier();
        cashier.setOpen(false);
        return cashier;
    }
}
