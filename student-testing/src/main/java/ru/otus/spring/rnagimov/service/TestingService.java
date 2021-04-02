package ru.otus.spring.rnagimov.service;

import ru.otus.spring.rnagimov.exception.TestingIoException;

/**
 * Сервис тестирования
 */
public interface TestingService {

    /**
     * Основной метод тестирования
     */
    void examine() throws TestingIoException;

    void examineAndOutput();

    /**
     * @return Количество верных ответов
     */
    int getUserScore();
}
