package br.com.sinqia.exceptions;

public class RegisterOrderNotFoundException extends RuntimeException {
    public RegisterOrderNotFoundException() {
        super("Register order not found");
    }
}
