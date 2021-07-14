package ru.otus.spring.rnagimov.libraryjdbc.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
@Import(GenreDaoJdbc.class)
@DisplayName("Класс GenreDaoJdbc")
class GenreDaoJdbcTest {

    public static final int INIT_GENRE_COUNT = 1;
    public static final long EXISTING_GENRE_ID = 3001L;
    public static final String EXISTING_GENRE_NAME = "Роман";

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("Возвращает корректное количество записей")
    void count() {
        assertThat(genreDao.count()).isEqualTo(INIT_GENRE_COUNT);
    }

    @Test
    @DisplayName("Добавляет жанр без ошибок")
    void insert() {
        long startCount = genreDao.count();
        Genre genre = new Genre(3100L, "New test genre");
        assertThatCode(() -> genreDao.insert(genre)).doesNotThrowAnyException();
        assertThat(genreDao.count()).isEqualTo(startCount + 1);
    }

    @Test
    @DisplayName("Возвращает ожидаемый список жанров")
    void getAll() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        List<Genre> actualGenreList = genreDao.getAll();
        assertThat(actualGenreList).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(expectedGenre);
    }

    @Test
    @DisplayName("Находит жанр по id")
    void getById() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = genreDao.getById(EXISTING_GENRE_ID);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("Не находит отсутствующий жанр")
    void getMissingById() {
        assertThatThrownBy(() -> genreDao.getById(2)).isInstanceOf(org.springframework.dao.EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("Корректно меняет название жанра")
    void update() {
        String newGenreName = "New name";
        Genre existingGenre = genreDao.getById(EXISTING_GENRE_ID);
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, newGenreName);
        genreDao.update(expectedGenre);
        Genre actualGenre = genreDao.getById(EXISTING_GENRE_ID);
        assertThat(actualGenre).isEqualTo(expectedGenre).isNotEqualTo(existingGenre);
    }

    @Test
    @DisplayName("Корректно удаляет жанр")
    void deleteById() {
        long startCount = genreDao.count();
        genreDao.deleteById(EXISTING_GENRE_ID);
        assertThat(genreDao.count()).isEqualTo(startCount - 1);
    }
}