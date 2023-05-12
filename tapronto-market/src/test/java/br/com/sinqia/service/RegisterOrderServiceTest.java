package br.com.sinqia.service;

import br.com.sinqia.dao.impl.RegisterOrderDAOImpl;
import br.com.sinqia.exceptions.RegisterOrderNotFoundException;
import br.com.sinqia.model.Cashier;
import br.com.sinqia.model.Order;
import br.com.sinqia.model.Register;
import br.com.sinqia.model.RegisterOrder;
import br.com.sinqia.service.impl.RegisterOrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterOrderServiceTest {
    @InjectMocks
    private RegisterOrderServiceImpl registerOrderService;

    @Mock
    private RegisterOrderDAOImpl registerOrderDAO;

    @Test
    @DisplayName("Deve buscar um registro de pedido por id")
    public void findByIdTest() {
        Long id = 1L;
        RegisterOrder registerOrder = createRegisterOrder();
        registerOrder.setId(id);
        when(registerOrderDAO.findById(anyLong())).thenReturn(Optional.of(registerOrder));
        assertEquals(registerOrder, registerOrderService.findById(id));
    }

    @Test
    @DisplayName("Dece lancar erro registro de pedido nao encotrado")
    public void exceptioRegisterOrderNotFoundFindById() {
        Long id = 1L;
        when(registerOrderDAO.findById(anyLong())).thenReturn(Optional.empty());
        RegisterOrderNotFoundException exception = assertThrows(RegisterOrderNotFoundException.class,
                () -> registerOrderService.findById(id));
        assertEquals("Register order not found", exception.getMessage());
    }

    @Test
    @DisplayName("Deve salvar um registro de pedido")
    public void saveTest() {
        RegisterOrder registerOrder = createRegisterOrder();

        RegisterOrder saveRegisterOrder = registerOrder;
        saveRegisterOrder.setId(1L);

        when(registerOrderDAO.save(any(RegisterOrder.class))).thenReturn(saveRegisterOrder);
        assertEquals(saveRegisterOrder, registerOrderService.save(registerOrder));
    }

    @Test
    @DisplayName("Deve lancar erro registro de pedido nulo")
    public void exceptionRegisterOrderNullSave() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> registerOrderService.save(null));
        assertEquals("Unexpected error! No register order to save", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar um registro de pedido")
    public void updateTest() {
        Long id = 1L;
        RegisterOrder registerOrder = createRegisterOrder();
        registerOrder.setId(id);
        when(registerOrderDAO.findById(anyLong())).thenReturn(Optional.of(registerOrder));

        RegisterOrder updateRegisterOrder = registerOrder;
        updateRegisterOrder.setOrder(createOrderUpdate());
        when(registerOrderDAO.save(any(RegisterOrder.class))).thenReturn(updateRegisterOrder);

        assertEquals(updateRegisterOrder, registerOrderService.update(1L, registerOrder));
    }

    @Test
    @DisplayName("Deve lancar erro registro de pedido nulo ao tentar atualizar")
    public void exceptionRegisterOrderNullUpdate() {
        Long id = 1L;
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> registerOrderService.update(id, null));
        assertEquals("Unexpected error! No register order to update", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lancar erro registro de pedido nao encontrado pelo id ao tentar atualizar")
    public void exceptionRegisterErrorNotFoundUpdate() {
        Long id = 1L;
        when(registerOrderDAO.findById(id)).thenReturn(Optional.empty());

        RegisterOrderNotFoundException exception = assertThrows(RegisterOrderNotFoundException.class,
                () -> registerOrderService.findById(id));
        assertEquals("Register order not found", exception.getMessage());
    }

    @Test
    @DisplayName("Deve remover um registro de pedido pelo id")
    public void deleteByIdTest() {
        Long id = 1L;
        RegisterOrder registerOrder = createRegisterOrder();
        registerOrder.setId(id);
        when(registerOrderDAO.findById(anyLong())).thenReturn(Optional.of(registerOrder));
        registerOrderService.deleteById(id);
        verify(registerOrderDAO, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deve lancar erro registro de pedido nao encontrado pelo id ao tentar remover")
    public void exceptionRegisterOrderNotFoundDeleteById() {
        Long id = 1L;
        RegisterOrder registerOrder = createRegisterOrder();
        registerOrder.setId(id);
        when(registerOrderDAO.findById(anyLong())).thenReturn(Optional.empty());
        RegisterOrderNotFoundException exception = assertThrows(RegisterOrderNotFoundException.class,
                () -> registerOrderService.deleteById(id));
        assertEquals("Register order not found", exception.getMessage());
    }


    private RegisterOrder createRegisterOrder() {
        Cashier cashier = new Cashier();
        cashier.setId(1L);
        cashier.setOpen(true);

        Register register = new Register();
        register.setId(1L);
        register.setDateTime(LocalDateTime.of(2023, 5, 12, 9, 20));
        register.setCashier(cashier);
        register.setOpeningBalance(new BigDecimal("10.0"));
        register.setClosedBalance(new BigDecimal("10.0"));

        Order order = new Order();
        order.setId(1L);
        order.setCreatedAt(LocalDate.of(2023,5,12));
        order.setAmount(new BigDecimal("12.3"));

        RegisterOrder registerOrder = new RegisterOrder();
        registerOrder.setRegister(register);
        registerOrder.setOrder(order);

        return registerOrder;
    }

    private Order createOrderUpdate() {
        Cashier cashier = new Cashier();
        cashier.setId(1L);
        cashier.setOpen(true);

        Register register = new Register();
        register.setId(1L);
        register.setDateTime(LocalDateTime.of(2023, 5, 12, 9, 20));
        register.setCashier(cashier);
        register.setOpeningBalance(new BigDecimal("10.0"));
        register.setClosedBalance(new BigDecimal("10.0"));

        Order order = new Order();
        order.setId(2L);
        order.setCreatedAt(LocalDate.of(2023,5,12));
        order.setAmount(new BigDecimal("14.3"));

        return order;
    }

}
