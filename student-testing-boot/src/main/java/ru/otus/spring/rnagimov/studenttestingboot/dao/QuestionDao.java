package ru.otus.spring.rnagimov.studenttestingboot.dao;

import ru.otus.spring.rnagimov.studenttestingboot.domain.Question;
import ru.otus.spring.rnagimov.studenttestingboot.exception.TestingException;

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
