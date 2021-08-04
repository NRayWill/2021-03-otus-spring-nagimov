package ru.otus.spring.rnagimov.libraryorm.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.rnagimov.libraryorm.model.Book;
import ru.otus.spring.rnagimov.libraryorm.model.Comment;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.otus.spring.rnagimov.libraryorm.repository.LibraryTestUtils.*;

@DataJpaTest
@Import(CommentRepositoryImpl.class)
@DisplayName("Репозиторий CommentRepository")
class CommentRepositoryTest {

    @Autowired
    private TestEntityManager tem;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Возвращает корректное количество записей")
    void count() {
        assertThat(commentRepository.count()).isEqualTo(getCommentCount());
    }

    @Test
    @DisplayName("Добавляет комментарий без ошибок")
    void insert() {
        long startCount = commentRepository.count();
        Book book = getTemItemById(tem, EXISTING_BOOK_ID, Book.class);
        Comment comment = new Comment(null, book, "Random Guy", "It's amazing!", new Date());
        AtomicLong newId = new AtomicLong();
        assertThatCode(() -> newId.set(commentRepository.insert(comment).getId())).doesNotThrowAnyException();
        assertThat(commentRepository.count()).isEqualTo(startCount + 1);
        assertThat(getTemItemById(tem, newId.get(), Comment.class)).isEqualTo(comment);
    }

    @Test
    @DisplayName("Возвращает ожидаемый список комментариев")
    void getAll() {
        Comment expectedComment = getExistingComment();
        List<Comment> actualCommentList = commentRepository.getAll();
        assertThat(actualCommentList).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(expectedComment);
    }

    @Test
    @DisplayName("Находит комментарий по id")
    void getById() {
        Comment expectedComment = getExistingComment();
        Comment actualComment = commentRepository.getById(EXISTING_COMMENT_ID);
        assertThat(actualComment).isEqualTo(expectedComment);
    }
    @Test
    @DisplayName("Корректно меняет текст комментария")
    void update() {
        Comment existingComment = getExistingComment();
        Comment expectedComment = new Comment(existingComment.getId(), existingComment.getBook(), existingComment.getCommentAuthor(), "Lorem ipsum dolor sit amet, duo ignota verear definiebas ut.", existingComment.getCreated());
        commentRepository.update(expectedComment);
        Comment actualComment = getExistingComment();
        assertThat(actualComment)
                .isEqualTo(expectedComment)
                .isNotEqualTo(existingComment);
    }

    @Test
    @DisplayName("Корректно удаляет комментарий")
    void deleteById() {
        Comment commentToDelete = getExistingComment();
        long startCount = getCommentCount();
        int deletedCount = commentRepository.deleteById(EXISTING_COMMENT_ID);
        assertThat(deletedCount).isEqualTo(1);
        assertThat(getCommentCount()).isEqualTo(startCount - 1);
        assertThat(tem.getEntityManager().find(Comment.class, EXISTING_COMMENT_ID)).isNotEqualTo(commentToDelete);
        assertThat(tem.getEntityManager().contains(commentToDelete)).isFalse();
    }

    private long getCommentCount() {
        return getTemCount(tem, Comment.class);
    }

    private Comment getExistingComment() {
        return getTemItemById(tem, LibraryTestUtils.EXISTING_COMMENT_ID, Comment.class);
    }
}