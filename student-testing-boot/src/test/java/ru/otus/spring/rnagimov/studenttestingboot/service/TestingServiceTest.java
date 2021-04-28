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
import ru.otus.spring.rnagimov.studenttestingboot.facade.LocalizedMessageFacade;
import ru.otus.spring.rnagimov.studenttestingboot.repository.QuestionRepository;
import ru.otus.spring.rnagimov.studenttestingboot.exception.TestingException;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;


@SpringBootTest
@EnableConfigurationProperties
public class TestingServiceTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private LocalizedMessageFacade localizedMessageFacade;

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
        testingService = new TestingServiceImpl(ioService, questionRepository, shuffleAnswerOptions, scoreToPass, localizedMessageFacade);
    }

    @Test
    public void testExamineRight() throws TestingException, IOException {
        replaceInput(1);
        Assertions.assertEquals(6, testingService.runTest().getCurrentScore());
    }

    @Test
    public void testExamine() throws TestingException, IOException {
        replaceInput(1, 1, 1, 1, 2, 2);
        Assertions.assertEquals(scoreToPass, testingService.runTest().getCurrentScore());
    }

    @Test
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
