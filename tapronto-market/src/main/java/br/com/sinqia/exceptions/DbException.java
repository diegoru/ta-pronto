package br.com.sinqia.exceptions;

public class DbException extends RuntimeException {
    public DbException(String msg) {
        super(msg);
    }
}
