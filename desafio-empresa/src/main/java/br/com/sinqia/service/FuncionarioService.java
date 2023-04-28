package br.com.sinqia.service;

import br.com.sinqia.model.Funcionario;

import java.util.List;

public interface FuncionarioService {
    List<Funcionario> findAll();
    Funcionario findById(Long id);
    Funcionario save(Funcionario funcionario);
    Funcionario update(Long id, Funcionario funcionario);
    void delete(Long id);
}
