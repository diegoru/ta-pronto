package br.com.sinqia.service;

import br.com.sinqia.model.Funcionario;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FuncionarioServiceImplTest {

    private final FuncionarioService funcionarioService = new FuncionarioServiceImpl();
    private final CargoService cargoService = new CargoServiceImpl();

    @BeforeAll
    static void carregarDados() {
        Carregar carregar = new Carregar();
        carregar.dados();
    }

    @Test
    void deveRetornarFuncionarioCadastrado () {
        Funcionario diego = funcionarioService.save(new Funcionario("Diego", new BigDecimal("10000"), cargoService.findById(1L)));
        assertEquals("Desenvolvedor", diego.getCargo().getDescricao());
    }

}