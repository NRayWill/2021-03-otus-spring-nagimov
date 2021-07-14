package ru.otus.spring.rnagimov.libraryjdbc.exception;

public class LibraryException extends Exception {

    public LibraryException(String errorMessage) {
        super(errorMessage);
    }

    public LibraryException(Exception ex) {
        super(ex);
    }
}
