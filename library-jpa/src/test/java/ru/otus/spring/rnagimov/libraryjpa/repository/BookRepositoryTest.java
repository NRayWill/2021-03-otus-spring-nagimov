package ru.otus.spring.rnagimov.libraryjpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.rnagimov.libraryjpa.model.Author;
import ru.otus.spring.rnagimov.libraryjpa.model.Book;
import ru.otus.spring.rnagimov.libraryjpa.model.Genre;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.*;
import static ru.otus.spring.rnagimov.libraryjpa.repository.LibraryTestUtils.*;

@DataJpaTest
@DisplayName("Репозиторий BookRepository")
class BookRepositoryTest {

    @Autowired
    private TestEntityManager tem;
    @Autowired
    private BookRepository bookRepository;

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
        assertThatCode(() -> newId.set(bookRepository.save(book).getId())).doesNotThrowAnyException();
        assertThat(getBookCount()).isEqualTo(startCount + 1);
        assertThat(getTemItemById(tem, newId.get(), Book.class)).isEqualTo(book);
    }

    @Test
    @DisplayName("Возвращает ожидаемый список книг")
    void getAll() {
        List<Book> actualBookList = bookRepository.findAll();
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
        assertThat(bookRepository.findById(2L)).isEmpty();
    }

    @Test
    @DisplayName("Корректно меняет книгу")
    void update() {
        String newBookName = "New book name";
        Book existingBook = getExistingBook();
        Book expectingBook = new Book(existingBook.getId(), newBookName, existingBook.getAuthor(), existingBook.getGenre());
        bookRepository.save(expectingBook);
        Book actualBook = getExistingBook();
        assertThat(actualBook)
                .isEqualTo(expectingBook)
                 .isNotEqualTo(existingBook);
    }

    @Test
    @DisplayName("Корректно удаляет книгу по id")
    void deleteById() {
        tem.getEntityManager().createQuery("delete from Comment c where c = " + EXISTING_COMMENT_ID).executeUpdate();

        Book bookToDelete  = getExistingBook();
        long startCount = getBookCount();
        bookRepository.deleteById(EXISTING_BOOK_ID);
        assertThat(getBookCount()).isEqualTo(startCount - 1);
        assertThat(tem.getEntityManager().find(Book.class, EXISTING_BOOK_ID)).isNotEqualTo(bookToDelete);
        assertThat(tem.getEntityManager().contains(bookToDelete)).isFalse();
    }

    private long getBookCount() {
        return getTemCount(tem, Book.class);
    }

    private Book getExistingBook() {
        return getTemItemById(tem, LibraryTestUtils.EXISTING_BOOK_ID, Book.class);
    }
}