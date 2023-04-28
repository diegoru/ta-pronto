package br.com.sinqia.service;

import br.com.sinqia.exception.ErroProcessamentoPagamentoException;
import br.com.sinqia.exception.FuncionarioNaoEncontradoException;
import br.com.sinqia.model.Funcionario;
import br.com.sinqia.model.PagamentoRealizado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProcessadorPagamentoImplTest {

    private final ProcessadorPagamento processadorPagamento = new ProcessadorPagamentoImpl();
    private final FuncionarioService funcionarioService = new FuncionarioServiceImpl();
    private final CargoService cargoService = new CargoServiceImpl();

    @BeforeAll
    static void carregarDados() {
        Carregar carregar = new Carregar();
        carregar.dados();
    }

    @Test
    void DadoFunciionarioDeveRealizarPagamentoComSucesso() {
        assertEquals(new BigDecimal("9500.00"), processadorPagamento.processar(1L).getValorPago());
    }

    @Test
    void DadoFuncionarioSemSalarioDeveOcorrerUmErro() {
        Funcionario danilo = funcionarioService.save(new Funcionario("Danilo", null, cargoService.findById(1L)));
        ErroProcessamentoPagamentoException erro = Assertions.assertThrowsExactly(ErroProcessamentoPagamentoException.class,
                () -> processadorPagamento.processar(4L));
        Assertions.assertEquals("Funcionario sem salario", erro.getMessage());
    }

    @Test
    public void DadoUmFuncionarioNuloDeveOcorrerErro() {
        FuncionarioNaoEncontradoException erro = Assertions.assertThrowsExactly(FuncionarioNaoEncontradoException.class,
                () -> processadorPagamento.processar(4L));
        Assertions.assertEquals("Funcionário não encontrado.", erro.getMessage());

    }

}