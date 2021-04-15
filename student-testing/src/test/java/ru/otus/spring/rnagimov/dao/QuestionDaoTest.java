package ru.otus.spring.rnagimov.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.rnagimov.Main;
import ru.otus.spring.rnagimov.exception.TestingException;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Main.class)
public class QuestionDaoTest {

    @Autowired
    private QuestionDao questionDao;

    @Test
    void getAllQuestions() throws TestingException {
        Assertions.assertEquals(6, questionDao.getAllQuestions().size());
    }
}