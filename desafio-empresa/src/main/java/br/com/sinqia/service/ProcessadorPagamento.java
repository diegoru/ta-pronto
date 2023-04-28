package br.com.sinqia.service;

import br.com.sinqia.model.Funcionario;
import br.com.sinqia.model.PagamentoRealizado;

public interface ProcessadorPagamento {
    PagamentoRealizado processar(Long id);
}
