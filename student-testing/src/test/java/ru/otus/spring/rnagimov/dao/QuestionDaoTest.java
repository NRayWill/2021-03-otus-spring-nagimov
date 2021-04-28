package ru.otus.spring.rnagimov.dao;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.rnagimov.Main;
import ru.otus.spring.rnagimov.domain.AnswerOption;
import ru.otus.spring.rnagimov.domain.Question;
import ru.otus.spring.rnagimov.exception.NoSuchQuestionFileException;
import ru.otus.spring.rnagimov.exception.TestingException;

import java.io.IOException;
import java.util.List;


@DisplayName("Класс QuestionDao")
public class QuestionDaoTest {

    private QuestionDao questionDao;

    @BeforeEach
    protected void setup() {
        questionDao = new QuestionDaoImpl("questions.csv");
    }

    @Test
    @DisplayName("Выбрасывает исключение при неправильном имени файла")
    void incorrectFileName() {
        questionDao = new QuestionDaoImpl("INCORECT_FILENAME");
        Assertions.assertThrows(NoSuchQuestionFileException.class,() -> questionDao.getAllQuestions());
    }

    @Test
    @DisplayName("Не выбрасывает исключение при правильном имени файла")
    void correctFileName() throws IOException, TestingException {
        questionDao.getAllQuestions();
    }

    @Test
    @DisplayName("Содержит 6 вопросов")
    void getAllQuestionsCount() throws TestingException, IOException {
        Assertions.assertEquals(6, questionDao.getAllQuestions().size());
    }

    @Test
    @DisplayName("№ вопросов идут по порядку")
    void CheckQuestionsNumbers() throws TestingException, IOException {
        List<Question> questions = questionDao.getAllQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Assertions.assertEquals(i + 1, questions.get(i).getQuestionNumber());
        }
    }

    @Test
    @DisplayName("Тексты вопросов не должны быть пустыми")
    void CheckQuestionsText() throws TestingException, IOException {
        List<Question> questions = questionDao.getAllQuestions();
        for (Question question : questions) {
            Assertions.assertFalse(StringUtils.isEmpty(question.getQuestionText()));
        }
    }

    @Test
    @DisplayName("Ответов в файле больше 1. Ответы не пустые")
    void CheckAnswers() throws TestingException, IOException {
        List<Question> questions = questionDao.getAllQuestions();
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