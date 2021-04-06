package ru.otus.spring.rnagimov.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.rnagimov.dao.QuestionDao;
import ru.otus.spring.rnagimov.domain.AnswerOption;
import ru.otus.spring.rnagimov.domain.Question;
import ru.otus.spring.rnagimov.domain.TestResult;
import ru.otus.spring.rnagimov.exception.TestingException;

import java.util.Collections;
import java.util.List;

/**
 * Реализация сервиса тестирования
 */
@Service
public class TestingServiceImpl implements TestingService {

    private final IoService io;
    private final QuestionDao questionDao;
    private final boolean shuffleAnswerOptions;
    private final int scoreToPass;

    public TestingServiceImpl(IoService io, QuestionDao questionDao, @Value("${shuffle.answer.options}") boolean shuffleAnswerOptions, @Value("${pass.score}") int scoreToPass) {
        this.io = io;
        this.questionDao = questionDao;
        this.shuffleAnswerOptions = shuffleAnswerOptions;
        this.scoreToPass = scoreToPass;
    }

    @Override
    public TestResult runTest() throws TestingException {
        List<Question> questionList = questionDao.getAllQuestions();
        TestResult testResult = new TestResult(scoreToPass);
        for (Question question : questionList) {
            askAQuestion(question, shuffleAnswerOptions);
            int userOption = io.readIntegerWithInterval(0, question.getAnswerOptionList().size());
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
     */
    private void askAQuestion(Question question, boolean shuffleAnswerOptions) {
        io.printLn(String.format("\nQuestion №%s: \n%s", question.getQuestionNumber(), question.getQuestionText()));

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
