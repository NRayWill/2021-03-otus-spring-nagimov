package ru.otus.spring.rnagimov.libraryjpa.exception;

public class NoElementWithSuchIdException extends LibraryException {
    public NoElementWithSuchIdException(String errorMessage) {
        super(errorMessage);
    }
}
