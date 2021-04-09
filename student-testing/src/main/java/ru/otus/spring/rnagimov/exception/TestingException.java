package ru.otus.spring.rnagimov.exception;

public class TestingException extends Exception {

    public TestingException(String errorMessage) {
        super(errorMessage);
    }

    public TestingException(Exception ex) {
        super(ex);
    }
}
