package br.com.sinqia.exceptions;

public class InsuficientQuantityOfProductException extends RuntimeException {
    public InsuficientQuantityOfProductException() {
        super("Insufficient quantity of product in stock");
    }
}
