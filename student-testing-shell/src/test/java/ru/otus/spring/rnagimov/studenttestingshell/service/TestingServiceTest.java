package ru.otus.spring.rnagimov.studenttestingshell.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.rnagimov.studenttestingshell.config.AppProperties;
import ru.otus.spring.rnagimov.studenttestingshell.exception.TestingException;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doAnswer;


@SpringBootTest
@EnableConfigurationProperties
public class TestingServiceTest {

    @MockBean
    private IoService ioService;

    @Autowired
    private TestingService testingService;

    @Autowired
    private AppProperties appProperties;

    private final static int ALL_QUESTION_COUNT = 6;

    @Test
    @DisplayName("Все ответы верные")
    public void testExamineRight() throws TestingException, IOException {
        replaceInput(1);
        Assertions.assertEquals(ALL_QUESTION_COUNT, testingService.runTest().getCurrentScore());
    }

    @Test
    @DisplayName("Половина ответов верна")
    public void testExamine() throws TestingException, IOException {
        replaceInput(1, 1, 1, 1, 2, 2);
        Assertions.assertEquals(appProperties.getPassScore(), testingService.runTest().getCurrentScore());
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
