package ru.otus.spring.rnagimov.studenttestingboot.exception;

public class TestingException extends Exception {

    public TestingException(String errorMessage) {
        super(errorMessage);
    }

    public TestingException(Exception ex) {
        super(ex);
    }
}
