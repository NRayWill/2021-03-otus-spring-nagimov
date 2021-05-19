package ru.otus.spring.rnagimov.studenttestingshell.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.rnagimov.studenttestingshell.config.AppProperties;
import ru.otus.spring.rnagimov.studenttestingshell.repository.QuestionRepository;
import ru.otus.spring.rnagimov.studenttestingshell.domain.AnswerOption;
import ru.otus.spring.rnagimov.studenttestingshell.domain.Question;
import ru.otus.spring.rnagimov.studenttestingshell.domain.TestResult;
import ru.otus.spring.rnagimov.studenttestingshell.exception.TestingException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Реализация сервиса тестирования
 */
@Service
public class TestingServiceImpl implements TestingService {

    private final IoService io;
    private final QuestionRepository questionRepository;
    private final LocalizedIoService localizedIoService;
    private final AppProperties appProperties;

    public TestingServiceImpl(IoService io, QuestionRepository questionRepository, LocalizedIoService localizedIoService, AppProperties appProperties) {
        this.io = io;
        this.questionRepository = questionRepository;
        this.localizedIoService = localizedIoService;
        this.appProperties = appProperties;
    }

    @Override
    public TestResult runTest() throws TestingException, IOException {
        List<Question> questionList = questionRepository.getAllQuestions();
        TestResult testResult = new TestResult(appProperties.getPassScore(), questionList.size());
        for (Question question : questionList) {
            askAQuestion(question, appProperties.isShuffleAnswerOptions());
            int userOption = io.readIntegerWithInterval(1, question.getAnswerOptionList().size());
            if (question.getAnswerOptionList().get(userOption - 1).isCorrect()) {
                testResult.increaseCurrentScore();
            }
        }
        return testResult;
    }

    /**
     * Метод печати вопроса с перемешанными вариантами ответа
     *
     * @param question Экземпляр вопроса
     * @param shuffleAnswerOptions Признак необходимости вывода вопросов в случайном порядке
     */
    private void askAQuestion(Question question, boolean shuffleAnswerOptions) {
        io.printLn("");
        localizedIoService.printLocalizedMessage("messages.question.n", question.getQuestionNumber());
        io.printLn(question.getQuestionText());

        List<AnswerOption> answerOptionList = question.getAnswerOptionList();
        if (shuffleAnswerOptions) {
            Collections.shuffle(answerOptionList);
        }

        for (int i = 0; i < answerOptionList.size(); i++) {
            AnswerOption answerOption = answerOptionList.get(i);
            int answerNumber = i + 1;
            io.printLn(answerNumber + ". " + answerOption.getAnswerText());
        }
    }
}
