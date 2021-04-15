package ru.otus.spring.rnagimov.studenttestingboot.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.rnagimov.studenttestingboot.domain.AnswerOption;
import ru.otus.spring.rnagimov.studenttestingboot.domain.Question;
import ru.otus.spring.rnagimov.studenttestingboot.exception.TestingException;
import ru.otus.spring.rnagimov.studenttestingboot.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionDaoImpl implements QuestionDao {

    private final Integer questionCount;
    private final MessageRepository messageRepository;

    private final List<Question> allQuestionList = new ArrayList<>();

    public QuestionDaoImpl(@Value("${questions.count}") Integer questionCount, MessageRepository messageRepository) {
        this.questionCount = questionCount;
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Question> getAllQuestions() throws TestingException {
        if (allQuestionList.isEmpty()) {
            fillQuestionList(questionCount);
        }
        return allQuestionList;
    }

    /**
     * Метод заполняет список всех вопросов {@link #allQuestionList}
     *
     * @param questionCount общее Количество вопросов
     */
    private void fillQuestionList(Integer questionCount) throws TestingException {
        for (int qN = 1; qN <= questionCount; qN++) {
            String questionLine = messageRepository.getMessage("question." + qN);
            String[] cellArray = questionLine.split(";");

            if (cellArray.length < 4) {
                throw new TestingException("The question-line must contain a number, a question and at least two answer options");
            }

            Question question = new Question();
            List<AnswerOption> answerOptionList = new ArrayList<>();
            question.setQuestionNumber(Integer.parseInt(cellArray[0]));
            question.setQuestionText(cellArray[1]);
            question.setAnswerOptionList(answerOptionList);

            for (int i = 2; i < cellArray.length; i++) {
                answerOptionList.add(new AnswerOption(cellArray[i], i == 2));
            }
            allQuestionList.add(question);
        }
    }
}