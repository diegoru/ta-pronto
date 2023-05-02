package br.com.sinqia.service;

import br.com.sinqia.exception.FuncionarioNaoEncontradoException;
import br.com.sinqia.model.Cargo;
import br.com.sinqia.model.Funcionario;
import br.com.sinqia.repository.CargoRepository;
import br.com.sinqia.repository.FuncionarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceImplTest {

    @InjectMocks
    private FuncionarioServiceImpl funcionarioService;
    @Mock
    private FuncionarioRepository funcionarioRepository;
    @Mock
    private CargoRepository cargoRepository;

    @Test
    public void deveRetornarListaFuncionarios(){
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Diego");

        Mockito.when(funcionarioRepository.findAll()).thenReturn(Arrays.asList(funcionario));
        assertEquals(Arrays.asList(funcionario), funcionarioService.findAll());
    }

    @Test
    public void deveBuscarFuncionarioPorId() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Diego");

        Mockito.when(funcionarioRepository.findById(Mockito.anyLong())).thenReturn(funcionario);
        assertEquals(funcionario, funcionarioService.findById(1L));
    }

    @Test
    public void deveRetornarFuncionarioNaoEncontradoException() {
        Mockito.when(funcionarioRepository.findById(Mockito.anyLong())).thenThrow(FuncionarioNaoEncontradoException.class);
        assertThrows(FuncionarioNaoEncontradoException.class, () -> funcionarioService.findById(1L));
    }

    @Test
    public void deveRetornarFuncionarioCadastrado () {
        Cargo cargo = new Cargo();
        cargo.setId(1L);
        cargo.setDescricao("Desenvolvedor");
        cargo.setAliquota(0.05);

        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Diego");
        funcionario.setSalario(new BigDecimal("10000"));
        funcionario.setCargo(cargo);

        Mockito.when(funcionarioRepository.save(Mockito.any(Funcionario.class))).thenReturn(funcionario);

        assertEquals(funcionario, funcionarioService.save(funcionario));
    }

    @Test
    public void deveRetornarFuncionarioAtualizado() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Diego");

        Funcionario funcionarioAtual = new Funcionario();
        funcionarioAtual.setNome("Diego Ruescas");

        Mockito.when(funcionarioRepository.update(Mockito.anyLong(), Mockito.any(Funcionario.class))).thenReturn(funcionarioAtual);
        assertEquals(funcionarioAtual, funcionarioService.update(1L, funcionarioAtual));
    }

    @Test
    public void deveRetornarFuncionarioNaoEncontradoExceptionUpdate() {
        Funcionario funcionarioAtual = new Funcionario();
        funcionarioAtual.setNome("Diego Ruescas");
        Mockito.when(funcionarioRepository.update(Mockito.anyLong(), Mockito.any(Funcionario.class))).thenThrow(FuncionarioNaoEncontradoException.class);
        assertThrows(FuncionarioNaoEncontradoException.class, () -> funcionarioService.update(1L, funcionarioAtual));
    }

    @Test
    public void verificaRemocaoFuncionario() {
        funcionarioService.delete(1L);
        Mockito.verify(funcionarioRepository).delete(1L);
    }

}