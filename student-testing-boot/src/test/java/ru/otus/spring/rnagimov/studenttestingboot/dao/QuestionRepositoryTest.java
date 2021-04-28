package ru.otus.spring.rnagimov.studenttestingboot.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.rnagimov.studenttestingboot.exception.TestingException;
import ru.otus.spring.rnagimov.studenttestingboot.repository.QuestionRepository;

import java.io.IOException;


@SpringBootTest
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void getAllQuestions() throws TestingException, IOException {
        Assertions.assertEquals(6, questionRepository.getAllQuestions().size());
    }
}