package br.com.sinqia.exceptions;

public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException() {
        super("Order item not found");
    }
}
