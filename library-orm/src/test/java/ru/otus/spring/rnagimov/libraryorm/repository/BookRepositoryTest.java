package ru.otus.spring.rnagimov.libraryorm.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.rnagimov.libraryorm.model.Author;
import ru.otus.spring.rnagimov.libraryorm.model.Book;
import ru.otus.spring.rnagimov.libraryorm.model.Genre;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.*;
import static ru.otus.spring.rnagimov.libraryorm.repository.LibraryTestUtils.*;

@DataJpaTest
@Import(value = {GenreRepositoryImpl.class, BookRepositoryImpl.class, AuthorRepositoryImpl.class, CommentRepositoryImpl.class})
@DisplayName("Репозиторий BookRepository")
class BookRepositoryTest {

    @Autowired
    private TestEntityManager tem;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Возвращает корректное количество записей")
    void count() {
        assertThat(bookRepository.count()).isEqualTo(getBookCount());
    }

    @Test
    @DisplayName("Добавляет книгу без ошибок")
    void insert() {
        long startCount = getBookCount();
        Genre genre = getTemItemById(tem, EXISTING_GENRE_ID, Genre.class);
        Author author = getTemItemById(tem, EXISTING_AUTHOR_ID, Author.class);
        Book book = new Book(null, "Новая книга", author, genre);
        AtomicLong newId = new AtomicLong();
        assertThatCode(() -> newId.set(bookRepository.insert(book))).doesNotThrowAnyException();
        assertThat(getBookCount()).isEqualTo(startCount + 1);
        assertThat(getTemItemById(tem, newId.get(), Book.class)).isEqualTo(book);
    }

    @Test
    @DisplayName("Возвращает ожидаемый список книг")
    void getAll() {
        List<Book> actualBookList = bookRepository.getAll();
        assertThat(actualBookList).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(getExistingBook());
    }

    @Test
    @DisplayName("Находит книгу по id")
    void getById() {
        Book actualBook = bookRepository.getById(EXISTING_BOOK_ID);
        assertThat(actualBook).isEqualTo(getExistingBook());
    }

    @Test
    @DisplayName("Не находит отсутствующую книгу")
    void getMissingById() {
        assertThat(bookRepository.getById(2)).isNull();
    }

    @Test
    @DisplayName("Корректно меняет книгу")
    void update() {
        String newBookName = "New book name";
        Book existingBook = getExistingBook();
        Book expectingBook = new Book(existingBook.getId(), newBookName, existingBook.getAuthor(), existingBook.getGenre());
        bookRepository.update(expectingBook);
        Book actualBook = getExistingBook();
        assertThat(actualBook)
                .isEqualTo(expectingBook)
                 .isNotEqualTo(existingBook);
    }

    @Test
    @DisplayName("Не удаляет книгу по id при наличии комментариев")
    void notDeleteByIdWithComments() {
        long startCount = getBookCount();
        assertThatThrownBy(() -> bookRepository.deleteById(EXISTING_BOOK_ID)).isInstanceOf(PersistenceException.class);
        assertThat(getBookCount()).isEqualTo(startCount);
    }

    @Test
    @DisplayName("Корректно удаляет книгу по id")
    void deleteById() {
        commentRepository.deleteById(EXISTING_COMMENT_ID);

        long startCount = getBookCount();
        int deletedCount = bookRepository.deleteById(EXISTING_BOOK_ID);
        assertThat(deletedCount).isEqualTo(1);
        assertThat(getBookCount()).isEqualTo(startCount - 1);
    }

    private long getBookCount() {
        return getTemCount(tem, Book.class);
    }

    private Book getExistingBook() {
        return getTemItemById(tem, LibraryTestUtils.EXISTING_BOOK_ID, Book.class);
    }
}