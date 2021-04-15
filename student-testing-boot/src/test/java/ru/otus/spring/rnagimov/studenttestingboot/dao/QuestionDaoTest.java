package ru.otus.spring.rnagimov.studenttestingboot.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.rnagimov.studenttestingboot.exception.TestingException;


@SpringBootTest
public class QuestionDaoTest {

    @Autowired
    private QuestionDao questionDao;

    @Test
    void getAllQuestions() throws TestingException {
        Assertions.assertEquals(6, questionDao.getAllQuestions().size());
    }
}