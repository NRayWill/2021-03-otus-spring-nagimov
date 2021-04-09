package ru.otus.spring.rnagimov.service;

import ru.otus.spring.rnagimov.domain.TestResult;
import ru.otus.spring.rnagimov.exception.TestingException;

/**
 * Сервис тестирования
 */
public interface TestingService {

    /**
     * Основной метод тестирования
     */
    TestResult runTest() throws TestingException;
}
