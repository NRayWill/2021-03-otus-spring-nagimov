package ru.otus.spring.rnagimov.studenttestingshell.repository;

import ru.otus.spring.rnagimov.studenttestingshell.domain.Question;
import ru.otus.spring.rnagimov.studenttestingshell.exception.TestingException;

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
