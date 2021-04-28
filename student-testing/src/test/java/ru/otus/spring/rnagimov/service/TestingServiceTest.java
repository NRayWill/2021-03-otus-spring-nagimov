package ru.otus.spring.rnagimov.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import ru.otus.spring.rnagimov.dao.QuestionDaoImpl;
import ru.otus.spring.rnagimov.exception.TestingException;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;


@DisplayName("Сервис тестирования")
public class TestingServiceTest {

    private IoService ioService;

    private TestingService testingService;

    private final int scoreToPass = 4;

    @BeforeEach
    protected void setup() {
        ioService = mock(IoServiceImpl.class);
        testingService = new TestingServiceImpl(
                ioService,
                new QuestionDaoImpl("questions.csv"),
                false,
                scoreToPass);
    }

    @Test
    @DisplayName("Все ответы верные")
    public void testExamineRight() throws TestingException, IOException {
        replaceInput(1);
        Assertions.assertEquals(6, testingService.runTest().getCurrentScore());
    }

    @Test
    @DisplayName("Половины ответа верна")
    public void testExamine() throws TestingException, IOException {
        replaceInput(1, 1, 1, 1, 2, 2);
        Assertions.assertEquals(scoreToPass, testingService.runTest().getCurrentScore());
    }

    @Test
    @DisplayName("Все ответы неверные")
    public void testExamineWrong() throws TestingException, IOException {
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
