package ru.otus.spring.rnagimov.exception;

import java.io.IOException;

public class TestingIoException extends IOException {
    public TestingIoException(IOException ioEx) {
        super(ioEx);
    }
}
