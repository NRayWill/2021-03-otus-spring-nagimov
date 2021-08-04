package ru.otus.spring.rnagimov.libraryorm.service;

/**
 * Сервис ввода-вывода
 */
public interface IoService {
    void printLn(String text);

    String readLn();
}