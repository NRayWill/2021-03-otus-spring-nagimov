package ru.otus.spring.rnagimov.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import ru.otus.spring.rnagimov.dao.QuestionDao;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@ContextConfiguration(locations = {"/spring-test-context.xml"})
public class TestingServiceTest {

    @Autowired
    QuestionDao questionDao;

    @Autowired
    TestingService testingService;

    @Autowired
    IoService ioService;

    @Test
    public void testQuestionCount() {
        Assert.assertEquals(6, questionDao.getAllQuestions().size());
    }

    @Test
    public void testExamineRight() {
        ((IoServiceFileInputImpl) ioService).updateFileScanner("answers-positive.csv");
        String result = testingService.examine();
        Assert.assertEquals("Your score: 6/6", result);
    }

    @Test
    public void testExamine() {
        ((IoServiceFileInputImpl) ioService).updateFileScanner("answers.csv");
        String result = testingService.examine();
        Assert.assertEquals("Your score: 3/6", result);
    }

    @Test
    public void testExamineWrong() {
        ((IoServiceFileInputImpl) ioService).updateFileScanner("answers-negative.csv");
        String result = testingService.examine();
        Assert.assertEquals("Your score: 0/6", result);
    }
}
