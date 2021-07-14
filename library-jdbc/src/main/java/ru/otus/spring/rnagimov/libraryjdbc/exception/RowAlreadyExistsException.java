package ru.otus.spring.rnagimov.libraryjdbc.exception;

public class RowAlreadyExistsException extends LibraryException {
    public RowAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }

    public RowAlreadyExistsException(Exception ex) {
        super(ex);
    }
}
