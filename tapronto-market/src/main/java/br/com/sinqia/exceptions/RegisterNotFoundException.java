package br.com.sinqia.exceptions;

public class RegisterNotFoundException extends RuntimeException {
    public RegisterNotFoundException() {
        super("Register not found");
    }
}
