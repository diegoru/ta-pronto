package br.com.sinqia.service;

import br.com.sinqia.exception.ErroProcessamentoPagamentoException;
import br.com.sinqia.model.Funcionario;
import br.com.sinqia.model.PagamentoRealizado;

public class ProcessadorPagamentoImpl implements ProcessadorPagamento {

    private final FuncionarioService funcionarioService = new FuncionarioServiceImpl();

    @Override
    public PagamentoRealizado processar(Long id) {
        Funcionario funcionario = funcionarioService.findById(id);
        if (funcionario.getSalario() == null) {
            throw new ErroProcessamentoPagamentoException("Funcionario sem salario");
        }
        return new PagamentoRealizado(funcionario, funcionarioService.getPagamento(id));
    }
}
