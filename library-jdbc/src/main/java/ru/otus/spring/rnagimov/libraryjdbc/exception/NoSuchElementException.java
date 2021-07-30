package ru.otus.spring.rnagimov.libraryjdbc.exception;

public class NoSuchElementException extends LibraryException {
    public NoSuchElementException(String errorMessage) {
        super(errorMessage);
    }
}
