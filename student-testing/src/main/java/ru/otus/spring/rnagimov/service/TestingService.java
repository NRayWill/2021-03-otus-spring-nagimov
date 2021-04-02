package ru.otus.spring.rnagimov.service;

import ru.otus.spring.rnagimov.exception.TestingIoException;

/**
 * Сервис тестирования
 */
public interface TestingService {

    /**
     * Основной метод тестирования
     *
     * @return Количество верных ответов
     */
    int examine() throws TestingIoException;

    void examineWithOutput();
}
