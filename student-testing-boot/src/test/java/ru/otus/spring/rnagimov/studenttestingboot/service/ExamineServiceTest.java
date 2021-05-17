package ru.otus.spring.rnagimov.studenttestingboot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.rnagimov.studenttestingboot.domain.TestResult;
import ru.otus.spring.rnagimov.studenttestingboot.exception.TestingException;

import java.io.IOException;

import static org.mockito.Mockito.*;


@SpringBootTest
@DisplayName("Сервис запуска тестирования")
class ExamineServiceTest {

    @MockBean
    private TestingService testingServiceMock;

    @MockBean
    private IoService ioServiceMock;

    @Autowired
    private ExamineService examineService;

    private TestResult getTestResultWithScore(int score) {
        TestResult testResult = new TestResult(4, 6);
        for (int i = 0; i < score; i++) {
            testResult.increaseCurrentScore();
        }
        return testResult;
    }

    @Test
    @DisplayName("При правильных ответах должно выводиться соответствующее сообщение")
    void examineSuccess() throws IOException, TestingException {
        when(testingServiceMock.runTest()).thenReturn(getTestResultWithScore(6));
        examineService.examine();

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ioServiceMock, atLeastOnce()).printLn(captor.capture());
        String resultString = captor.getValue();
        Assertions.assertTrue(resultString.endsWith(", your score: 6/6. The exam has been passed."));
    }

    @Test
    @DisplayName("При неправильных ответах должно выводиться соответствующее сообщение")
    void examineFailed() throws IOException, TestingException {
        when(testingServiceMock.runTest()).thenReturn(getTestResultWithScore(0));
        examineService.examine();

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(ioServiceMock, atLeastOnce()).printLn(captor.capture());
        String resultString = captor.getValue();
        Assertions.assertTrue(resultString.endsWith(", your score: 0/6. The exam hasn't been passed."));
    }
}