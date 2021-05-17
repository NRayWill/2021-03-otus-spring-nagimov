package ru.otus.spring.rnagimov.studenttestingboot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.rnagimov.studenttestingboot.facade.LocalizedMessageFacade;
import ru.otus.spring.rnagimov.studenttestingboot.repository.QuestionRepository;
import ru.otus.spring.rnagimov.studenttestingboot.domain.AnswerOption;
import ru.otus.spring.rnagimov.studenttestingboot.domain.Question;
import ru.otus.spring.rnagimov.studenttestingboot.domain.TestResult;
import ru.otus.spring.rnagimov.studenttestingboot.exception.TestingException;

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
    private final boolean shuffleAnswerOptions;
    private final int scoreToPass;
    private final LocalizedMessageFacade localizedMessageFacade;

    public TestingServiceImpl(IoService io, QuestionRepository questionRepository, @Value("${shuffle.answer.options}") boolean shuffleAnswerOptions, @Value("${pass.score}") int scoreToPass, LocalizedMessageFacade localizedMessageFacade) {
        this.io = io;
        this.questionRepository = questionRepository;
        this.shuffleAnswerOptions = shuffleAnswerOptions;
        this.scoreToPass = scoreToPass;
        this.localizedMessageFacade = localizedMessageFacade;
    }

    @Override
    public TestResult runTest() throws TestingException, IOException {
        List<Question> questionList = questionRepository.getAllQuestions();
        TestResult testResult = new TestResult(scoreToPass, questionList.size());
        for (Question question : questionList) {
            askAQuestion(question, shuffleAnswerOptions);
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
        localizedMessageFacade.printLocalizedMessageFromBundle("messages.question.n", question.getQuestionNumber());
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
