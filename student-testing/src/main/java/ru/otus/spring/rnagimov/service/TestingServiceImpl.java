package ru.otus.spring.rnagimov.service;

import lombok.AllArgsConstructor;
import ru.otus.spring.rnagimov.dao.QuestionDao;
import ru.otus.spring.rnagimov.domain.AnswerOption;
import ru.otus.spring.rnagimov.domain.Question;
import ru.otus.spring.rnagimov.exception.TestingIoException;

import java.util.Collections;
import java.util.List;

/**
 * Реализация сервиса тестирования
 */
@AllArgsConstructor
public class TestingServiceImpl implements TestingService {

    private IoService io;
    private QuestionDao questionDao;
    private boolean shuffleAnswerOptions;

    @Override
    public int examine() throws TestingIoException {
        List<Question> questionList = questionDao.getAllQuestions();
        int rightAnswersCount = 0;
        for (Question question : questionList) {
            askAQuestion(question, shuffleAnswerOptions);
            int userOption = io.readIntegerWithInterval(0, question.getAnswerOptionList().size());

            if (question.getAnswerOptionList().get(userOption - 1).isCorrect()) {
                rightAnswersCount++;
            }
        }
        return rightAnswersCount;
    }

    @Override
    public void examineWithOutput() {
        try {
            int rightAnswersCount = examine();
            String result = String.format("Your score: %s/%s", rightAnswersCount, questionDao.getAllQuestions().size());
            io.printLn(String.format("\n%s\nThank you!", result));
            io.readLn();
        } catch (TestingIoException ex) {
            io.printLn(String.format("ERROR: %s", ex.getMessage()));
        }
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
