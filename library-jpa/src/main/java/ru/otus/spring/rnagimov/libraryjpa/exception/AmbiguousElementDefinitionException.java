package ru.otus.spring.rnagimov.libraryjpa.exception;

public class AmbiguousElementDefinitionException extends LibraryException {
    public AmbiguousElementDefinitionException(String errorMessage) {
        super(errorMessage);
    }
}
