package ru.otus.spring.rnagimov.libraryjpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.rnagimov.libraryjpa.model.Author;
import ru.otus.spring.rnagimov.libraryjpa.model.Book;
import ru.otus.spring.rnagimov.libraryjpa.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.otus.spring.rnagimov.libraryjpa.repository.LibraryTestUtils.*;

@DataJpaTest
@DisplayName("Репозиторий BookRepository")
class BookRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    public static final int INIT_BOOK_COUNT = 1;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("Возвращает корректное количество записей")
    void count() {
        assertThat(bookRepository.count()).isEqualTo(INIT_BOOK_COUNT);
    }

    @Test
    @DisplayName("Добавляет книгу без ошибок")
    void insert() {
        long startCount = bookRepository.count();
        Optional<Genre> genreOpt = genreRepository.findById(EXISTING_GENRE_ID);
        Optional<Author> authorOpt = authorRepository.findById(EXISTING_AUTHOR_ID);
        assertThat(authorOpt).isPresent();
        assertThat(genreOpt).isPresent();
        Book book = new Book(null, "Новая книга", authorOpt.get(), genreOpt.get());
        assertThatCode(() -> bookRepository.save(book)).doesNotThrowAnyException();
        assertThat(bookRepository.count()).isEqualTo(startCount + 1);
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
        Optional<Book> actualBookOpt = bookRepository.findById(EXISTING_BOOK_ID);
        assertThat(actualBookOpt).isPresent().contains(getExistingBook());
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
        Optional<Book> existingBookOpt =bookRepository.findById(EXISTING_BOOK_ID);
        assertThat(existingBookOpt).isNotEmpty();
        Book existingBook = existingBookOpt.get();
        em.detach(existingBook);
        Book expectingBook = new Book(existingBook.getId(), newBookName, existingBook.getAuthor(), existingBook.getGenre());
        bookRepository.save(expectingBook);
        em.detach(expectingBook);
        Book actualBook = bookRepository.getById(EXISTING_BOOK_ID);
        assertThat(actualBook).isEqualTo(expectingBook).isNotEqualTo(existingBook);
    }

    @Test
    @DisplayName("Корректно удаляет книгу по id")
    void deleteById() {
        commentRepository.deleteById(EXISTING_COMMENT_ID);
        long startCount = bookRepository.count();
        bookRepository.deleteById(EXISTING_BOOK_ID);
        assertThat(bookRepository.count()).isEqualTo(startCount - 1);
    }

}