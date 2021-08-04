package ru.otus.spring.rnagimov.libraryjpa.service;

/**
 * Сервис ввода-вывода
 */
public interface IoService {
    void printLn(String text);

    String readLn();
}