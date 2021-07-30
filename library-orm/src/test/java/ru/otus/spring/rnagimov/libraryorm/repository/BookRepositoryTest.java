package ru.otus.spring.rnagimov.libraryorm.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.rnagimov.libraryorm.dto.AuthorDto;
import ru.otus.spring.rnagimov.libraryorm.dto.BookDto;
import ru.otus.spring.rnagimov.libraryorm.dto.GenreDto;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static ru.otus.spring.rnagimov.libraryorm.repository.LibraryTestUtils.*;

@DataJpaTest
@Import(value = {GenreRepositoryImpl.class, BookRepositoryImpl.class, AuthorRepositoryImpl.class, CommentRepositoryImpl.class})
@DisplayName("Репозиторий BookRepository")
class BookRepositoryTest {

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
        GenreDto genre = genreRepository.getById(EXISTING_GENRE_ID);
        AuthorDto author = authorRepository.getById(EXISTING_AUTHOR_ID);
        BookDto book = new BookDto(null, "Новая книга", author, genre);
        assertThatCode(() -> bookRepository.insert(book)).doesNotThrowAnyException();
        assertThat(bookRepository.count()).isEqualTo(startCount + 1);
    }

    @Test
    @DisplayName("Возвращает ожидаемый список книг")
    void getAll() {
        List<BookDto> actualBookList = bookRepository.getAll();
        assertThat(actualBookList).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(getExistingBook());
    }

    @Test
    @DisplayName("Находит книгу по id")
    void getById() {
        BookDto actualBook = bookRepository.getById(EXISTING_BOOK_ID);
        assertThat(actualBook).isEqualTo(getExistingBook());
    }

    @Test
    @DisplayName("Не находит отсутствующую книгу")
    void getMissingById() {
        assertThatThrownBy(() -> bookRepository.getById(2)).isInstanceOf(NoResultException.class);
    }

    @Test
    @DisplayName("Корректно меняет книгу")
    void update() {
        String newBookName = "New book name";
        BookDto existingBook = bookRepository.getById(EXISTING_BOOK_ID);
        BookDto expectingBook = new BookDto(existingBook.getId(), newBookName, existingBook.getAuthor(), existingBook.getGenre());
        int updatedCount = bookRepository.update(expectingBook);
        assertThat(updatedCount).isEqualTo(1);
        BookDto actualBook = bookRepository.getById(EXISTING_BOOK_ID);
        assertThat(actualBook).isEqualTo(expectingBook).isNotEqualTo(existingBook);
    }

    @Test
    @DisplayName("Не удаляет книгу по id при наличии комментариев")
    void notDeleteByIdWithComments() {
        long startCount = bookRepository.count();
        assertThatThrownBy(() -> bookRepository.deleteById(EXISTING_BOOK_ID)).isInstanceOf(PersistenceException.class);
        assertThat(bookRepository.count()).isEqualTo(startCount);
    }

    @Test
    @DisplayName("Корректно удаляет книгу по id")
    void deleteById() {
        commentRepository.deleteById(EXISTING_COMMENT_ID);

        long startCount = bookRepository.count();
        int deletedCount = bookRepository.deleteById(EXISTING_BOOK_ID);
        assertThat(deletedCount).isEqualTo(1);
        assertThat(bookRepository.count()).isEqualTo(startCount - 1);
    }

}