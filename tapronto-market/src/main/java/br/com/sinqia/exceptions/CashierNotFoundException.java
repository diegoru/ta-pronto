package br.com.sinqia.exceptions;

public class CashierNotFoundException extends RuntimeException {
    public CashierNotFoundException() {
        super("Cashier not found");
    }
}
