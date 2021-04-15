package ru.otus.spring.rnagimov.studenttestingboot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.rnagimov.studenttestingboot.dao.QuestionDao;
import ru.otus.spring.rnagimov.studenttestingboot.exception.TestingException;
import ru.otus.spring.rnagimov.studenttestingboot.repository.MessageRepository;

import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;


@SpringBootTest
@EnableConfigurationProperties
public class TestingServiceTest {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private MessageRepository messageRepository;

    @Mock
    private IoService ioService;

    private TestingService testingService;

    @Value("${shuffle.answer.options}")
    private boolean shuffleAnswerOptions;

    @Value("${pass.score}")
    private int scoreToPass;

    @BeforeEach
    protected void setup() {
        ioService = mock(IoServiceImpl.class);
        testingService = new TestingServiceImpl(ioService, questionDao, shuffleAnswerOptions, scoreToPass, messageRepository);
    }

    @Test
    public void testExamineRight() throws TestingException {
        replaceInput(1);
        Assertions.assertEquals(6, testingService.runTest().getCurrentScore());
    }

    @Test
    public void testExamine() throws TestingException {
        replaceInput(1, 1, 1, 1, 2, 2);
        Assertions.assertEquals(scoreToPass, testingService.runTest().getCurrentScore());
    }

    @Test
    public void testExamineWrong() throws TestingException {
        replaceInput(2);
        Assertions.assertEquals(0, testingService.runTest().getCurrentScore());
    }

    private void replaceInput(int... input) {
        AtomicInteger callNumber = new AtomicInteger(-1);
        Answer<Integer> answer = invocation -> {
            callNumber.getAndIncrement();
            int index = input.length > 1 ? callNumber.intValue() : 0;
            return input[index];
        };
        doAnswer(answer).when(ioService).readIntegerWithInterval(anyInt(), anyInt());
    }
}
