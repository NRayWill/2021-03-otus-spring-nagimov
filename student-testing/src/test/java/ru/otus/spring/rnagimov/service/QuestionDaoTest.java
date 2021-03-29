package ru.otus.spring.rnagimov.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.rnagimov.dao.QuestionDao;
import ru.otus.spring.rnagimov.exception.TestingIoException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"/spring-test-context.xml"})
public class QuestionDaoTest {

    @Autowired
    private QuestionDao questionDao;

    @Test
    public void testQuestionCount() throws TestingIoException {
        Assertions.assertEquals(6, questionDao.getAllQuestions().size());
    }
}