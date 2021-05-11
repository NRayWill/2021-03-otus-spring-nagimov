package ru.otus.spring.rnagimov.service;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import ru.otus.spring.rnagimov.dao.QuestionDao;
import ru.otus.spring.rnagimov.dao.QuestionDaoImpl;
import ru.otus.spring.rnagimov.domain.AnswerOption;
import ru.otus.spring.rnagimov.domain.Question;
import ru.otus.spring.rnagimov.exception.TestingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;


@DisplayName("Сервис тестирования")
public class TestingServiceTest {

    private IoService ioServiceMock;
    private TestingService testingService;

    private final int scoreToPass = 4;
    private final static int ALL_QUESTION_COUNT = 6;

    // Заглушка списка вопросов с ответами
    private static List<Question> questionListStub = getQuestionListStub();

    @BeforeEach
    protected void setup() throws IOException, TestingException {
        ioServiceMock = mock(IoServiceImpl.class);

        QuestionDao questionDaoMock = mock(QuestionDaoImpl.class);
        when(questionDaoMock.getAllQuestions()).thenReturn(questionListStub);

        testingService = new TestingServiceImpl(
                ioServiceMock,
                questionDaoMock,
                false,
                scoreToPass);
    }

    private static List<Question> getQuestionListStub() {
        List<Question> questionList = Lists.newArrayList();
        for (int i = 0; i < ALL_QUESTION_COUNT; i++) {
            Question question = new Question();
            List<AnswerOption> answerOptionList = new ArrayList<>();
            question.setAnswerOptionList(answerOptionList);
            for (int j = 0; j < 2; j++) {
                answerOptionList.add(new AnswerOption("test answer", j == 0));
            }
            questionList.add(question);
        }
        return questionList;
    }

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
        doAnswer(answer).when(ioServiceMock).readIntegerWithInterval(anyInt(), anyInt());
    }
}
