package ru.otus.spring.rnagimov.libraryjdbc.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Author;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Book;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(value = {GenreDaoJdbc.class, BookDaoJdbc.class, AuthorDaoJdbc.class})
@DisplayName("Класс BookDaoJdbc")
class BookDaoJdbcTest {

    public static final int INIT_BOOK_COUNT = 1;

    public static final long EXISTING_BOOK_ID = 1001L;
    public static final long EXISTING_AUTHOR_ID = 2001L;
    public static final long EXISTING_GENRE_ID = 3001L;

    public static final String EXISTING_BOOK_TITLE = "Сто лет одиночества";

    public static final String EXISTING_AUTHOR_NAME = "Габриэль";
    public static final String EXISTING_AUTHOR_MIDDLENAME = "Гарсиа";
    public static final String EXISTING_AUTHOR_SURNAME = "Маркес";

    public static final String EXISTING_GENRE_NAME = "Роман";

    @Autowired
    private BookDao bookDao;
    @Autowired
    private GenreDao genreDao;
    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("Возвращает корректное количество записей")
    void count() {
        assertThat(bookDao.count()).isEqualTo(INIT_BOOK_COUNT);
    }

    @Test
    @DisplayName("Добавляет книгу без ошибок")
    void insert() {
        long startCount = bookDao.count();
        Genre genre = genreDao.getById(EXISTING_GENRE_ID);
        Author author = authorDao.getById(EXISTING_AUTHOR_ID);
        Book book = new Book(1100L, "Новая книга", author, genre);
        assertThatCode(() -> bookDao.insert(book)).doesNotThrowAnyException();
        assertThat(bookDao.count()).isEqualTo(startCount + 1);
    }

    @Test
    @DisplayName("Возвращает ожидаемый список книг")
    void getAll() {
        List<Book> actualBookList = bookDao.getAll();
        assertThat(actualBookList).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(getExistingBook());
    }

    @Test
    @DisplayName("Находит книгу по id")
    void getById() {
        Book actualBook = bookDao.getById(EXISTING_BOOK_ID);
        assertThat(actualBook).isEqualTo(getExistingBook());
    }

    @Test
    @DisplayName("Не находит отсутствующую книгу")
    void getMissingById() {
        assertThatThrownBy(() -> bookDao.getById(2)).isInstanceOf(org.springframework.dao.EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("Корректно меняет книгу")
    void update() {
        String newBookName = "New book name";
        Book existingBook = bookDao.getById(EXISTING_BOOK_ID);
        Book expectingBook = new Book(existingBook.getId(), newBookName, existingBook.getAuthor(), existingBook.getGenre());
        bookDao.update(expectingBook);
        Book actualBook = bookDao.getById(EXISTING_BOOK_ID);
        assertThat(actualBook).isEqualTo(expectingBook).isNotEqualTo(existingBook);
    }

    @Test
    @DisplayName("Корректно удаляет книгу по id")
    void deleteById() {
        long startCount = bookDao.count();
        bookDao.deleteById(EXISTING_BOOK_ID);
        assertThat(bookDao.count()).isEqualTo(startCount - 1);
    }

    private Book getExistingBook() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        return new Book(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, expectedAuthor, expectedGenre);
    }
}