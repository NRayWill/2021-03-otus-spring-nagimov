package ru.otus.spring.rnagimov.studenttestingboot.service;

/**
 * Сервис ввода-вывода
 */
public interface IoService {
    void printLn(String text);

    String readLn();

    int readIntegerWithInterval(int leftBorder, int rightBorder);
}