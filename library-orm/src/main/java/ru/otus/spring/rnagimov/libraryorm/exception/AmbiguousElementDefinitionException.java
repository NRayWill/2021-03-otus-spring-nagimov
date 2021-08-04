package ru.otus.spring.rnagimov.libraryorm.exception;

public class AmbiguousElementDefinitionException extends LibraryException {
    public AmbiguousElementDefinitionException(String errorMessage) {
        super(errorMessage);
    }
}
