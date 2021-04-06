package ru.otus.spring.rnagimov.dao;

import ru.otus.spring.rnagimov.domain.Question;
import ru.otus.spring.rnagimov.exception.TestingException;

import java.util.List;

/**
 * DAO вопросов
 */
public interface QuestionDao {

    /**
     * Получение всех элементов вопросов
     *
     * @return Список всех вопросов
     */
    List<Question> getAllQuestions() throws TestingException;
}
