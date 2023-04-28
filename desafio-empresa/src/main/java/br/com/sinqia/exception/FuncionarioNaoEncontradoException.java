package br.com.sinqia.exception;

public class FuncionarioNaoEncontradoException extends RuntimeException {
    public FuncionarioNaoEncontradoException() {
        super("Funcionário não encontrado.");
    }
}
