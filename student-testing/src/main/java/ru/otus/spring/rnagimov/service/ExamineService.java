package ru.otus.spring.rnagimov.service;

import ru.otus.spring.rnagimov.domain.Student;
import ru.otus.spring.rnagimov.domain.TestResult;
import ru.otus.spring.rnagimov.exception.TestingException;

/**
 * Сервис проведения экзамена
 */
public interface ExamineService {
    void examine();
}
