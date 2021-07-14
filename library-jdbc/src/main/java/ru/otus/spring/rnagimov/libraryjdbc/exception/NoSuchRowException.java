package ru.otus.spring.rnagimov.libraryjdbc.exception;

public class NoSuchRowException extends LibraryException {

    public NoSuchRowException(String errorMessage) {
        super(errorMessage);
    }

    public NoSuchRowException(Exception ex) {
        super(ex);
    }
}
