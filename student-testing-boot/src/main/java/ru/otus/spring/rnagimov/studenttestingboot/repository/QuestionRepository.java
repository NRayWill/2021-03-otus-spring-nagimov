package ru.otus.spring.rnagimov.studenttestingboot.repository;

import ru.otus.spring.rnagimov.studenttestingboot.domain.Question;
import ru.otus.spring.rnagimov.studenttestingboot.exception.TestingException;

import java.io.IOException;
import java.util.List;

/**
 * DAO вопросов
 */
public interface QuestionRepository {

    /**
     * Получение всех элементов вопросов
     *
     * @return Список всех вопросов
     */
    List<Question> getAllQuestions() throws TestingException, IOException;
}
