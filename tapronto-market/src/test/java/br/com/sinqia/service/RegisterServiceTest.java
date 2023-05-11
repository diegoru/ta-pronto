package br.com.sinqia.service;

import br.com.sinqia.dao.impl.RegisterDAOImpl;
import br.com.sinqia.exceptions.RegisterNotFoundException;
import br.com.sinqia.model.Cashier;
import br.com.sinqia.model.Register;
import br.com.sinqia.service.impl.RegisterServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {

    @InjectMocks
    private RegisterServiceImpl registerService;

    @Mock
    private RegisterDAOImpl registerDAO;

    @Test
    @DisplayName("Deve listar todos os registros")
    public void findAllTest() {
        Register register= createRegister();
        register.setId(1L);

        List<Register> registerList = Arrays.asList(register);

        when(registerDAO.findAll()).thenReturn(registerList);

        assertEquals(registerList, registerService.findAll());
    }

    @Test
    @DisplayName("Deve buscar registros por id")
    public void findByIdTest() {
        Long id = 1L;
        Register register = createRegister();
        when(registerDAO.findById(anyLong())).thenReturn(Optional.of(register));
        assertEquals(register, registerService.findById(id));
    }

    @Test
    @DisplayName("Deve lancar erro registro nao encontrado pelo id")
    public void exceptionNotFoundfindById() {
        Long id = 1L;
        when(registerDAO.findById(anyLong())).thenReturn(Optional.empty());
        RegisterNotFoundException exception = assertThrows(RegisterNotFoundException.class, () -> registerService.findById(1L));
        assertEquals("Register not found", exception.getMessage());
    }

    @Test
    @DisplayName("Deve salvar registro")
    public void saveTest() {
        Register register = createRegister();
        Register saveRegister = register;
        saveRegister.setId(1L);
        when(registerDAO.save(any(Register.class))).thenReturn(saveRegister);
        assertEquals(saveRegister, registerService.save(register));
    }

    @Test
    @DisplayName("Deve lancar um erro registro nulo")
    public void exceptionSaveRegisterNull() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> registerService.save(null));
        assertEquals("Unexpected error! No register to save", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar registro")
    public void updateTest() {
        Long id = 1L;
        Register register = createRegister();
        register.setId(id);
        when(registerDAO.findById(anyLong())).thenReturn(Optional.of(register));

        Register registerUpdate = register;
        registerUpdate.setOpeningBalance(new BigDecimal("60.00"));
        when(registerDAO.save(any(Register.class))).thenReturn(registerUpdate);

        assertEquals(registerUpdate, registerService.update(id, register));
    }

    @Test
    @DisplayName("Deve lancar erro registro nulo ao tentar atualizar")
    public void exceptionRegisterNullUpdate() {
        Long id = 1L;
        RuntimeException exception = assertThrows(RuntimeException.class, () -> registerService.update(id, null));
        assertEquals("Unexpected error! No register to update", exception.getMessage());
    }


    @Test
    @DisplayName("Deve lancar erro registro nao encontrado ao tentar atualizar")
    public void exceptionRegisterNotfoundUpdate() {
        Long id = 1L;
        Register register = createRegister();
        register.setId(id);

        when(registerDAO.findById(anyLong())).thenReturn(Optional.empty());

        RegisterNotFoundException exception = assertThrows(RegisterNotFoundException.class, () -> registerService.update(id, register));
        assertEquals("Register not found", exception.getMessage());
    }

    @Test
    @DisplayName("Deve remover um registro")
    public void deleteByIdTest() {
        Long id = 1L;
        Register register = createRegister();
        register.setId(id);
        when(registerDAO.findById(anyLong())).thenReturn(Optional.of(register));

        registerService.deleteById(id);

        verify(registerDAO, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deve lancar erro registro nao encontrado ao tentar remover")
    public void exceptionRegisterNotFoundDeleteById() {
        Long id = 1L;
        Register register = createRegister();
        register.setId(id);
        when(registerDAO.findById(anyLong())).thenReturn(Optional.empty());

        RegisterNotFoundException exception = assertThrows(RegisterNotFoundException.class, () -> registerService.deleteById(id));
        assertEquals("Register not found", exception.getMessage());
    }

    private Register createRegister() {
        Cashier cashier = new Cashier();
        cashier.setId(1L);
        cashier.setOpen(true);

        Register register = new Register();
        register.setDateTime(LocalDateTime.of(2023, 5,11,16,13,11));
        register.setCashier(cashier);
        register.setOpeningBalance(new BigDecimal("100"));

        return register;
    }

}
