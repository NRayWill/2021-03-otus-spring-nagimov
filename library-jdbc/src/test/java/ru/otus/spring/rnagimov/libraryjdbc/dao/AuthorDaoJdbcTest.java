package ru.otus.spring.rnagimov.libraryjdbc.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@JdbcTest
@Import(AuthorDaoJdbc.class)
@DisplayName("Класс AuthorDaoJdbc")
class AuthorDaoJdbcTest {

    public static final int INIT_AUTHOR_COUNT = 1;
    public static final long EXISTING_AUTHOR_ID = 2001L;
    public static final String EXISTING_AUTHOR_NAME = "Габриэль";
    public static final String EXISTING_AUTHOR_MIDDLENAME = "Гарсиа";
    public static final String EXISTING_AUTHOR_SURNAME = "Маркес";

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("Возвращает корректное количество записей")
    void count() {
        assertThat(authorDao.count()).isEqualTo(INIT_AUTHOR_COUNT);
    }

    @Test
    @DisplayName("Добавляет автора без ошибок")
    void insert() {
        long startCount = authorDao.count();
        Author author = new Author(2100L, "И", "О", "Ф");
        assertThatCode(() -> authorDao.insert(author)).doesNotThrowAnyException();
        assertThat(authorDao.count()).isEqualTo(startCount + 1);
    }

    @Test
    @DisplayName("Возвращает ожидаемый список авторов")
    void getAll() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        List<Author> actualAuthorList = authorDao.getAll();
        assertThat(actualAuthorList).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(expectedAuthor);
    }

    @Test
    @DisplayName("Находит автора по id")
    void getById() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        Author actualAuthor = authorDao.getById(EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }
    @Test
    @DisplayName("Корректно меняет ФИО автора")
    void update() {
        String newAuthorName = "New name";
        String newAuthorMiddleName = "New middle name";
        String newAuthorSurname = "New surname";
        Author existingAuthor = authorDao.getById(EXISTING_AUTHOR_ID);
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, newAuthorName, newAuthorMiddleName, newAuthorSurname);
        authorDao.update(expectedAuthor);
        Author actualAuthor = authorDao.getById(EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).isEqualTo(expectedAuthor).isNotEqualTo(existingAuthor);
    }

    @Test
    @DisplayName("Корректно удаляет автора")
    void deleteById() {
        Author author = new Author(null, "И", "О", "Ф");
        long newAuthorId = authorDao.insert(author);

        long startCount = authorDao.count();
        authorDao.deleteById(newAuthorId);
        assertThat(authorDao.count()).isEqualTo(startCount - 1);
    }
}