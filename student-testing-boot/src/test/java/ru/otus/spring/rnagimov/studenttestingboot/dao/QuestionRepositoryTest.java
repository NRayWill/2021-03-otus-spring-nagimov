package ru.otus.spring.rnagimov.studenttestingboot.dao;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.rnagimov.studenttestingboot.domain.AnswerOption;
import ru.otus.spring.rnagimov.studenttestingboot.domain.Question;
import ru.otus.spring.rnagimov.studenttestingboot.exception.NoSuchQuestionFileException;
import ru.otus.spring.rnagimov.studenttestingboot.exception.TestingException;
import ru.otus.spring.rnagimov.studenttestingboot.repository.QuestionRepository;
import ru.otus.spring.rnagimov.studenttestingboot.repository.QuestionRepositoryImpl;
import ru.otus.spring.rnagimov.studenttestingboot.service.MessageService;

import java.io.IOException;
import java.util.List;


@SpringBootTest
@DisplayName("Репозиторий QuestionRepository")
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    @DisplayName("Не выбрасывает исключение при правильном имени файла")
    void correctFileName() throws IOException, TestingException {
        questionRepository.getAllQuestions();
    }

    @Test
    @DisplayName("Содержит 6 вопросов")
    void getAllQuestionsCount() throws TestingException, IOException {
        Assertions.assertEquals(6, questionRepository.getAllQuestions().size());
    }

    @Test
    @DisplayName("№ вопросов идут по порядку")
    void CheckQuestionsNumbers() throws TestingException, IOException {
        List<Question> questions = questionRepository.getAllQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Assertions.assertEquals(i + 1, questions.get(i).getQuestionNumber());
        }
    }

    @Test
    @DisplayName("Тексты вопросов не должны быть пустыми")
    void CheckQuestionsText() throws TestingException, IOException {
        List<Question> questions = questionRepository.getAllQuestions();
        for (Question question : questions) {
            Assertions.assertFalse(StringUtils.isEmpty(question.getQuestionText()));
        }
    }

    @Test
    @DisplayName("Ответов в файле больше 1. Ответы не пустые")
    void CheckAnswers() throws TestingException, IOException {
        List<Question> questions = questionRepository.getAllQuestions();
        for (Question question : questions) {
            List<AnswerOption> answerOptionList = question.getAnswerOptionList();

            Assertions.assertFalse(answerOptionList.isEmpty());
            Assertions.assertTrue(answerOptionList.size() > 1);
            for (AnswerOption answerOption : answerOptionList) {
                Assertions.assertFalse(StringUtils.isEmpty(answerOption.getAnswerText()));
            }
        }
    }
}