package ru.otus.spring.rnagimov.studenttestingshell.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.rnagimov.studenttestingshell.config.AppProperties;
import ru.otus.spring.rnagimov.studenttestingshell.domain.AnswerOption;
import ru.otus.spring.rnagimov.studenttestingshell.domain.Question;
import ru.otus.spring.rnagimov.studenttestingshell.exception.NoSuchQuestionFileException;
import ru.otus.spring.rnagimov.studenttestingshell.exception.TestingException;

import java.io.IOException;
import java.util.List;


@DisplayName("Репозиторий QuestionRepository")
class QuestionRepositoryTest {

    private QuestionRepository questionRepository;

    private final static int CORRECT_QUESTIONS_COUNT = 6;

    @BeforeEach
    protected void setup() {
        AppProperties appProperties = new AppProperties();
        appProperties.setLocale("");
        appProperties.setQuestionFileName("questions");
        questionRepository = new QuestionRepositoryImpl(appProperties);
    }

    @Test
    @DisplayName("Выбрасывает исключение при неправильном имени файла")
    void throwsExceptionWithIncorrectFilename() {
        AppProperties appProperties = new AppProperties();
        appProperties.setLocale("");
        appProperties.setQuestionFileName("INCORRECT_FILENAME");
        questionRepository = new QuestionRepositoryImpl(appProperties);
        Assertions.assertThatThrownBy(() -> questionRepository.getAllQuestions())
                .isInstanceOf(NoSuchQuestionFileException.class)
                .hasMessageContaining("No such question file");
    }

    @Test
    @DisplayName("Не выбрасывает исключение при правильном имени файла")
    void noExceptionWithCorrectFilename() throws IOException, TestingException {
        questionRepository.getAllQuestions();
        Assertions.assertThatNoException();
    }

    @Test
    @DisplayName("Содержит " + CORRECT_QUESTIONS_COUNT + " вопросов")
    void getAllQuestionsCount() throws TestingException, IOException {
        Assertions.assertThat(questionRepository.getAllQuestions()).hasSize(CORRECT_QUESTIONS_COUNT);
    }

    @Test
    @DisplayName("№ вопросов идут по порядку, тексты вопросов не пустые, ответов в файле больше 1 и они не пустые")
    void checkQuestionsAndAnswers() throws TestingException, IOException {
        List<Question> questions = questionRepository.getAllQuestions();
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