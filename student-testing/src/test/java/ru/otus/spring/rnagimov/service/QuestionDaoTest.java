package ru.otus.spring.rnagimov.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.rnagimov.Main;
import ru.otus.spring.rnagimov.dao.QuestionDao;
import ru.otus.spring.rnagimov.exception.TestingException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Main.class)
public class QuestionDaoTest {

    @Autowired
    private QuestionDao questionDao;

    @Test
    public void testQuestionCount() throws TestingException {
        Assertions.assertEquals(6, questionDao.getAllQuestions().size());
    }
}
