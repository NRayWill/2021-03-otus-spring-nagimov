package ru.otus.spring.rnagimov.libraryjpa.exception;

public class LibraryException extends Exception {

    public LibraryException(String errorMessage) {
        super(errorMessage);
    }

    public LibraryException(Exception ex) {
        super(ex);
    }
}
