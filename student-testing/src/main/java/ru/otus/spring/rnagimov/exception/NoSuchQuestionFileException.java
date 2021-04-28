package ru.otus.spring.rnagimov.exception;

public class NoSuchQuestionFileException extends TestingException {
    public NoSuchQuestionFileException(String errorMessage) {
        super(errorMessage);
    }
}
