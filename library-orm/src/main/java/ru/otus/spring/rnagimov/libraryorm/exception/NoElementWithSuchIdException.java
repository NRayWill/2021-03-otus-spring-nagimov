package ru.otus.spring.rnagimov.libraryorm.exception;

public class NoElementWithSuchIdException extends LibraryException {
    public NoElementWithSuchIdException(String errorMessage) {
        super(errorMessage);
    }
}
