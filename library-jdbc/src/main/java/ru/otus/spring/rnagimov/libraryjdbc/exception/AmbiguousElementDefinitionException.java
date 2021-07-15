package ru.otus.spring.rnagimov.libraryjdbc.exception;

public class AmbiguousElementDefinitionException extends LibraryException {
    public AmbiguousElementDefinitionException(String errorMessage) {
        super(errorMessage);
    }
}
