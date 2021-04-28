package ru.otus.spring.rnagimov.studenttestingboot.exception;

public class IncorrectQuestionFileException extends TestingException {
    public IncorrectQuestionFileException(String errorMessage) {
        super(errorMessage);
    }
}
