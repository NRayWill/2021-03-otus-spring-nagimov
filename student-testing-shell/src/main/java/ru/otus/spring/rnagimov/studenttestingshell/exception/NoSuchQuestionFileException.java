package ru.otus.spring.rnagimov.studenttestingshell.exception;

public class NoSuchQuestionFileException extends TestingException {
    public NoSuchQuestionFileException(String errorMessage) {
        super(errorMessage);
    }
}
