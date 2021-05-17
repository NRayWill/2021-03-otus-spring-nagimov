package ru.otus.spring.rnagimov.studenttestingboot.exception;

public class NoSuchQuestionFileException extends TestingException {
    public NoSuchQuestionFileException(String errorMessage) {
        super(errorMessage);
    }
}
