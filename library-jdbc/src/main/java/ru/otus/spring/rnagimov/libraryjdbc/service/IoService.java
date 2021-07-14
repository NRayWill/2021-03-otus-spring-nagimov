package ru.otus.spring.rnagimov.libraryjdbc.service;

/**
 * Сервис ввода-вывода
 */
public interface IoService {
    void printLn(String text);

    String readLn();
}