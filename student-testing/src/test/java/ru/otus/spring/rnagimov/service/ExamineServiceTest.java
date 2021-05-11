package ru.otus.spring.rnagimov.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.otus.spring.rnagimov.domain.TestResult;
import ru.otus.spring.rnagimov.exception.TestingException;

import java.io.IOException;

import static org.mockito.Mockito.*;


@DisplayName("Сервис запуска тестирования")
class ExamineServiceTest {

    private TestingService testingServiceMock;
    private IoService ioServiceMock;
    private ExamineService examineService;

    @BeforeEach
    protected void setup() {
        testingServiceMock = mock(TestingService.class);
        ioServiceMock = mock(IoServiceImpl.class);
        examineService = new ExamineServiceImpl(testingServiceMock, ioServiceMock);
    }

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