package br.com.sinqia.repository;

import br.com.sinqia.exception.FuncionarioNaoEncontradoException;
import br.com.sinqia.model.Funcionario;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FuncionarioRepository {

    private static final List<Funcionario> funcionarios = new ArrayList<>();
    private static final AtomicLong counter = new AtomicLong();

    public List<Funcionario> findAll() {
        return funcionarios;
    }

    public Funcionario findById(Long id) {
        return funcionarios.stream()
                .filter(idFuncionario -> id.equals(idFuncionario.getId()))
                .findAny()
                .orElseThrow(FuncionarioNaoEncontradoException::new);
    }

    public Funcionario save(Funcionario funcionario) {
        funcionario.setId(counter.incrementAndGet());
        funcionarios.add(funcionario);
        return funcionario;
    }

    public Funcionario update(Long id, Funcionario funcionario) {
        Funcionario funcionarioAtual = findById(id);
        funcionarioAtual.setNome(funcionario.getNome());
        funcionarioAtual.setCargo(funcionarioAtual.getCargo());
        funcionarioAtual.setSalario(funcionarioAtual.getSalario());
        return funcionarioAtual;
    }

    public boolean existById(Long id) {
        return funcionarios.stream().anyMatch(idFuncioanrio -> id.equals(idFuncioanrio.getId()));
    }

    public void delete(Long id) {
        if (!existById(id)) {
            throw new FuncionarioNaoEncontradoException();
        }
        Funcionario funcionario = findById(id);
        funcionarios.remove(funcionario);
    }
}
