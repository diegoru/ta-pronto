package br.com.ruescas.erp.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.ruescas.erp.model.Empresa;
import br.com.ruescas.erp.repository.Empresas;
import br.com.ruescas.erp.util.Transacional;

public class CadastroEmpresaService implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Empresas empresas;
	
	@Transacional
	public void salvar(Empresa empresa) {
		empresas.guardar(empresa);
	}
	
	@Transacional
	public void excluir(Empresa empresa) {
		empresas.remover(empresa);
	}

}
