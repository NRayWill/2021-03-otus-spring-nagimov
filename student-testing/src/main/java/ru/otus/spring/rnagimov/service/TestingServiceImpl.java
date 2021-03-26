package ru.otus.spring.rnagimov.service;

import lombok.AllArgsConstructor;
import ru.otus.spring.rnagimov.dao.QuestionDao;
import ru.otus.spring.rnagimov.domain.AnswerOption;
import ru.otus.spring.rnagimov.domain.Question;

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
    public String examine() {
        List<Question> questionList = questionDao.getAllQuestions();
        int rightAnswersCount = 0;
        for (Question question : questionList) {
            int rightAnswerNumber = askAQuestion(question, shuffleAnswerOptions);
            if (getAndCheckAnAnswer(rightAnswerNumber, question.getAnswerOptionList().size())) {
                rightAnswersCount++;
            }
        }
        String result = String.format("Your score: %s/%s", rightAnswersCount, questionList.size());
        io.printLn(String.format("\n%s", result));
        return result;
    }

    /**
     * Метод печати вопроса с перемешанными вариантами ответа
     *
     * @param question Экземпляр вопроса
     * @return Номер правильного варианта
     */
    private int askAQuestion(Question question, boolean shuffleAnswerOptions) {
        io.printLn(String.format("\nQuestion №%s:", question.getQuestionNumber()));
        io.printLn(question.getQuestionText());
        List<AnswerOption> answerOptionList = question.getAnswerOptionList();
        if (shuffleAnswerOptions) {
            Collections.shuffle(answerOptionList);
        }
        int rightAnswerNumber = -1;
        for (int i = 0; i < answerOptionList.size(); i++) {
            AnswerOption answerOption = answerOptionList.get(i);
            int answerNumber = i + 1;
            if (answerOption.isCorrect()) {
                rightAnswerNumber = answerNumber;
            }
            io.printLn(answerNumber + ". " + answerOption.getAnswerText());
        }
        return rightAnswerNumber;
    }

    /**
     * Метод получает ответ и проверяет его правильность
     *
     * @param rightOptionNumber Номер правильного варианта
     * @param optionsCount      Количество вариантов ответа на вопрос (необходимо для валидации ответа пользователя)
     * @return True - выбран верный овтет, иначе - False
     */
    private boolean getAndCheckAnAnswer(int rightOptionNumber, int optionsCount) {
        int userOption = -1;
        boolean optionIsValid = false;
        while (!optionIsValid) {
            try {
                userOption = Integer.parseInt(io.readLn());
                if (userOption < 1 || userOption > optionsCount) {
                    io.printLn(String.format("You answer must be greater than 0 and less than %s", optionsCount + 1));
                } else {
                    optionIsValid = true;
                }
            } catch (NumberFormatException nfe) {
                io.printLn("Type a number of option please");
            }
        }
        return userOption == rightOptionNumber;
    }
}
