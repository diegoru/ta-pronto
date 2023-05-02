package br.com.sinqia.service;

import br.com.sinqia.model.Funcionario;
import br.com.sinqia.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class FuncionarioServiceImpl implements FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;


    @Override
    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    @Override
    public Funcionario findById(Long id) {
        return funcionarioRepository.findById(id);
    }

    @Override
    public Funcionario save(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    @Override
    public Funcionario update(Long id, Funcionario funcionario) {
        return funcionarioRepository.update(id, funcionario);
    }

    @Override
    public void delete(Long id) {
        funcionarioRepository.delete(id);
    }


    @Override
    public BigDecimal getPagamento(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        return funcionario.getSalario().multiply(BigDecimal.valueOf(1 - funcionario.getCargo().getAliquota()));
    }
}
