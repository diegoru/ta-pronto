package br.com.sinqia.exception;

public class FuncionarioSemSalarioException extends RuntimeException {
        public FuncionarioSemSalarioException() {
        super("Funcionario sem salario");
    }
}
