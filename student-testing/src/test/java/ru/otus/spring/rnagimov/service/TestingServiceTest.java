package ru.otus.spring.rnagimov.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.rnagimov.Main;
import ru.otus.spring.rnagimov.dao.QuestionDao;

import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = Main.class)
public class TestingServiceTest {

    @Autowired
    private QuestionDao questionDao;

    private IoService ioService;
    private TestingService testingService;

    @Value("${shuffle.answer.options}")
    private boolean shuffleAnswerOptions;

    @Value("${pass.score}")
    private int scoreToPass;

    @Value("${print.during.test}")
    private boolean printDuringTests;

    @BeforeAll
    protected void setup() {
        if (printDuringTests) {
            ioService = spy(IoServiceImpl.class);
            doReturn("").when(ioService).readLn();
        } else {
            ioService = mock(IoServiceImpl.class);
        }
        testingService = new TestingServiceImpl(ioService, questionDao, shuffleAnswerOptions, scoreToPass);
    }

    @Test
    public void testExamineRight() {
        replaceInput(1);
        testingService.examineAndOutput();
        Assertions.assertEquals(6, testingService.getUserScore());
    }

    @Test
    public void testExamine() {
        replaceInput(1, 1, 1, 1, 2, 2);
        testingService.examineAndOutput();
        Assertions.assertEquals(scoreToPass, testingService.getUserScore());
    }

    @Test
    public void testExamineWrong() {
        replaceInput(2);
        testingService.examineAndOutput();
        Assertions.assertEquals(0, testingService.getUserScore());
    }

    private void replaceInput(int... input) {
        AtomicInteger callNumber = new AtomicInteger(-1);
        Answer<Integer> answer = invocation -> {
            callNumber.getAndIncrement();
            int index = input.length > 1 ? callNumber.intValue() : 0;
            if (printDuringTests) {
                ioService.printLn(Integer.toString(input[index]));
            }
            return input[index];
        };
        doAnswer(answer).when(ioService).readIntegerWithInterval(anyInt(), anyInt());
    }
}
