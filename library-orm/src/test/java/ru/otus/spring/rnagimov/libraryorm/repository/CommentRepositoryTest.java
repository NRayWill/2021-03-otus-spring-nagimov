package ru.otus.spring.rnagimov.libraryorm.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.rnagimov.libraryorm.dto.BookDto;
import ru.otus.spring.rnagimov.libraryorm.dto.CommentDto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.otus.spring.rnagimov.libraryorm.repository.LibraryTestUtils.*;

@DataJpaTest
@Import(CommentRepositoryImpl.class)
@DisplayName("Репозиторий CommentRepository")
class CommentRepositoryTest {

    public static final int INIT_COMMENT_COUNT = 1;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Возвращает корректное количество записей")
    void count() {
        assertThat(commentRepository.count()).isEqualTo(INIT_COMMENT_COUNT);
    }

    @Test
    @DisplayName("Добавляет комментарий без ошибок")
    void insert() {
        long startCount = commentRepository.count();
        BookDto book = getExistingBook();
        CommentDto comment = new CommentDto(null, book, "Rangom Guy", "It's amazing!", new Timestamp(new Date().getTime()));
        assertThatCode(() -> commentRepository.insert(comment)).doesNotThrowAnyException();
        assertThat(commentRepository.count()).isEqualTo(startCount + 1);
    }

    @Test
    @DisplayName("Возвращает ожидаемый список комментариев")
    void getAll() {
        CommentDto expectedComment = new CommentDto(EXISTING_COMMENT_ID, getExistingBook(), EXISTING_COMMENT_AUTHOR, EXISTING_COMMENT_TEXT, EXISTING_COMMENT_DATE);
        List<CommentDto> actualCommentList = commentRepository.getAll();
        assertThat(actualCommentList).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(expectedComment);
    }

    @Test
    @DisplayName("Находит комментарий по id")
    void getById() {
        CommentDto expectedComment = new CommentDto(EXISTING_COMMENT_ID, getExistingBook(), EXISTING_COMMENT_AUTHOR, EXISTING_COMMENT_TEXT, EXISTING_COMMENT_DATE);
        CommentDto actualComment = commentRepository.getById(EXISTING_COMMENT_ID);
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }
    @Test
    @DisplayName("Корректно меняет текст комментария")
    void update() {
        CommentDto existingComment = commentRepository.getById(EXISTING_COMMENT_ID);
        CommentDto expectedComment = new CommentDto(EXISTING_COMMENT_ID, getExistingBook(), EXISTING_COMMENT_AUTHOR, "Lorem ipsum dolor sit amet, duo ignota verear definiebas ut.", EXISTING_COMMENT_DATE);
        int updatedCount = commentRepository.update(expectedComment);
        assertThat(updatedCount).isEqualTo(1);
        CommentDto actualComment = commentRepository.getById(EXISTING_COMMENT_ID);
        assertThat(actualComment).isEqualTo(expectedComment).isNotEqualTo(existingComment);
    }

    @Test
    @DisplayName("Корректно удаляет комментарий")
    void deleteById() {
        long startCount = commentRepository.count();
        int deletedCount = commentRepository.deleteById(EXISTING_COMMENT_ID);
        assertThat(deletedCount).isEqualTo(1);
        assertThat(commentRepository.count()).isEqualTo(startCount - 1);
    }
}