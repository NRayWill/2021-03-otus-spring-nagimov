package ru.otus.spring.rnagimov.libraryjdbc.exception;

public class AmbiguousRowDefinitionException extends LibraryException {
    public AmbiguousRowDefinitionException(String errorMessage) {
        super(errorMessage);
    }

    public AmbiguousRowDefinitionException(Exception ex) {
        super(ex);
    }
}
