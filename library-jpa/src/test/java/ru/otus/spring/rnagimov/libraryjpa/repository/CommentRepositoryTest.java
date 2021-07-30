package ru.otus.spring.rnagimov.libraryjpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.rnagimov.libraryjpa.model.Book;
import ru.otus.spring.rnagimov.libraryjpa.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.otus.spring.rnagimov.libraryjpa.repository.LibraryTestUtils.*;

@DataJpaTest
@DisplayName("Репозиторий CommentRepository")
class CommentRepositoryTest {

    @PersistenceContext
    private EntityManager em;

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
        Book book = getExistingBook();
        Comment comment = new Comment(null, "Rangom Guy", book, "It's amazing!", new Timestamp(new Date().getTime()));
        assertThatCode(() -> commentRepository.save(comment)).doesNotThrowAnyException();
        assertThat(commentRepository.count()).isEqualTo(startCount + 1);
    }

    @Test
    @DisplayName("Возвращает ожидаемый список комментариев")
    void getAll() {
        Comment expectedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_AUTHOR, getExistingBook(), EXISTING_COMMENT_TEXT, EXISTING_COMMENT_DATE);
        List<Comment> actualCommentList = commentRepository.findAll();
        assertThat(actualCommentList).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(expectedComment);
    }

    @Test
    @DisplayName("Находит комментарий по id")
    void getById() {
        Comment expectedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_AUTHOR, getExistingBook(), EXISTING_COMMENT_TEXT, EXISTING_COMMENT_DATE);
        Comment actualComment = commentRepository.getById(EXISTING_COMMENT_ID);
        assertThat(actualComment).isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("Находит комментарии по id книги")
    void getByBook(){
        Comment expectedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_AUTHOR, getExistingBook(), EXISTING_COMMENT_TEXT, EXISTING_COMMENT_DATE);
        assertThat(commentRepository.findByBookOrderByCreatedDesc(getExistingBook())).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(expectedComment);
    }

    @Test
    @DisplayName("Находит комментарий по частичному имени комментатора")
     void getByCommentAuthorLike(){
        Comment expectedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_AUTHOR, getExistingBook(), EXISTING_COMMENT_TEXT, EXISTING_COMMENT_DATE);
        assertThat(commentRepository.findByCommentAuthorLikeIgnoreCaseOrderByCreatedDesc("%wiki%")).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(expectedComment);
    }

    @Test
    @DisplayName("Корректно меняет текст комментария")
    void update() {
        Optional<Comment> existingCommentOpt = commentRepository.findById(EXISTING_COMMENT_ID);
        assertThat(existingCommentOpt).isPresent();
        Comment existingComment = existingCommentOpt.get();
        em.detach(existingComment);
        Comment expectedComment = new Comment(EXISTING_COMMENT_ID, EXISTING_COMMENT_AUTHOR, getExistingBook(), "Lorem ipsum dolor sit amet, duo ignota verear definiebas ut.", EXISTING_COMMENT_DATE);
        commentRepository.save(expectedComment);
        em.detach(expectedComment);
        Comment actualComment = commentRepository.getById(EXISTING_COMMENT_ID);
        assertThat(actualComment).isEqualTo(expectedComment).isNotEqualTo(existingComment);
    }

    @Test
    @DisplayName("Корректно удаляет комментарий")
    void deleteById() {
        long startCount = commentRepository.count();
        commentRepository.deleteById(EXISTING_COMMENT_ID);
        assertThat(commentRepository.count()).isEqualTo(startCount - 1);
    }
}