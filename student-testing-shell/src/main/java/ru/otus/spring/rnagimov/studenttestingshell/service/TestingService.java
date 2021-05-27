package ru.otus.spring.rnagimov.studenttestingshell.service;

import ru.otus.spring.rnagimov.studenttestingshell.domain.TestResult;
import ru.otus.spring.rnagimov.studenttestingshell.exception.TestingException;

import java.io.IOException;

/**
 * Сервис тестирования
 */
public interface TestingService {

    /**
     * Основной метод тестирования
     */
    TestResult runTest() throws TestingException, IOException;
}
