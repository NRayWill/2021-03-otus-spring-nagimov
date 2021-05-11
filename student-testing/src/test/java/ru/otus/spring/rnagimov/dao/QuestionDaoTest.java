package ru.otus.spring.rnagimov.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.rnagimov.domain.AnswerOption;
import ru.otus.spring.rnagimov.domain.Question;
import ru.otus.spring.rnagimov.exception.NoSuchQuestionFileException;
import ru.otus.spring.rnagimov.exception.TestingException;

import java.io.IOException;
import java.util.List;


@DisplayName("Класс QuestionDao")
public class QuestionDaoTest {

    private QuestionDao questionDao;

    private final static int CORRECT_QUESTIONS_COUNT = 6;

    @BeforeEach
    protected void setup() {
        questionDao = new QuestionDaoImpl("questions.csv");
    }

    @Test
    @DisplayName("Выбрасывает исключение при неправильном имени файла")
    void throwsExceptionWithIncorrectFilename() {
        questionDao = new QuestionDaoImpl("INCORRECT_FILENAME");
        Assertions.assertThatThrownBy(() -> questionDao.getAllQuestions())
                .isInstanceOf(NoSuchQuestionFileException.class)
                .hasMessageContaining("No such question file");
    }

    @Test
    @DisplayName("Не выбрасывает исключение при правильном имени файла")
    void noExceptionWithCorrectFilename() throws IOException, TestingException {
        questionDao.getAllQuestions();
        Assertions.assertThatNoException();
    }

    @Test
    @DisplayName("Содержит " + CORRECT_QUESTIONS_COUNT + " вопросов")
    void getAllQuestionsCount() throws TestingException, IOException {
        Assertions.assertThat(questionDao.getAllQuestions()).hasSize(CORRECT_QUESTIONS_COUNT);
    }

    @Test
    @DisplayName("№ вопросов идут по порядку, тексты вопросов не пустые, ответов в файле больше 1 и они не пустые")
    void checkQuestionsAndAnswers() throws TestingException, IOException {
        List<Question> questions = questionDao.getAllQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            // № вопросов идут по порядку
            Assertions.assertThat(question).hasFieldOrPropertyWithValue("questionNumber", i + 1);

            // тексты вопросов не пустые
            Assertions.assertThat(question.getQuestionText()).isNotEmpty();

            List<AnswerOption> answerOptionList = question.getAnswerOptionList();

            // ответов в файле больше 1
            Assertions.assertThat(answerOptionList).isNotEmpty().hasSizeGreaterThan(1);

            // ответы не пустые
            for (AnswerOption answerOption : answerOptionList) {
                Assertions.assertThat(answerOption.getAnswerText()).isNotEmpty();
            }
        }
    }
}