package br.com.sinqia.service;

import br.com.sinqia.model.Cargo;
import br.com.sinqia.model.Funcionario;

import java.math.BigDecimal;

public class Carregar {

    private final CargoService cargoService = new CargoServiceImpl();
    private final FuncionarioService funcionarioService = new FuncionarioServiceImpl();

    public void dados() {
        cargoService.save(new Cargo("Desenvolvedor", 0.05));
        cargoService.save(new Cargo("Analista de sistema", 0.07));
        cargoService.save(new Cargo("Departamento pessoal", 0.06));

        funcionarioService.save(new Funcionario("Diego", new BigDecimal("10000"), cargoService.findById(1L)));
        funcionarioService.save(new Funcionario("Kleberton", new BigDecimal("12000"), cargoService.findById(2L)));
        funcionarioService.save(new Funcionario("Diego", new BigDecimal("5000"), cargoService.findById(3L)));
    }
}
