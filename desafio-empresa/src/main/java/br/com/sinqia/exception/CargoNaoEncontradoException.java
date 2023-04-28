package br.com.sinqia.exception;

public class CargoNaoEncontradoException extends RuntimeException {
    public CargoNaoEncontradoException() {
        super("Cargo não encontrado.");
    }
}
