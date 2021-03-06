package ru.otus.spring.rnagimov.studenttestingboot.service;

import ru.otus.spring.rnagimov.studenttestingboot.domain.TestResult;
import ru.otus.spring.rnagimov.studenttestingboot.exception.TestingException;

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
