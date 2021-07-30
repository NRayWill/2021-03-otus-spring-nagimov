package ru.otus.spring.rnagimov.libraryjpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.rnagimov.libraryjpa.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.otus.spring.rnagimov.libraryjpa.repository.LibraryTestUtils.EXISTING_GENRE_ID;
import static ru.otus.spring.rnagimov.libraryjpa.repository.LibraryTestUtils.EXISTING_GENRE_NAME;

@DataJpaTest
@DisplayName("Репозиторий GenreRepository")
class GenreRepositoryTest {
    @PersistenceContext
    private EntityManager em;

    public static final int INIT_GENRE_COUNT = 1;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Возвращает корректное количество записей")
    void count() {
        assertThat(genreRepository.count()).isEqualTo(INIT_GENRE_COUNT);
    }

    @Test
    @DisplayName("Добавляет жанр без ошибок")
    void insert() {
        long startCount = genreRepository.count();
        Genre genre = new Genre(null, "New test genre");
        assertThatCode(() -> genreRepository.save(genre)).doesNotThrowAnyException();
        assertThat(genreRepository.count()).isEqualTo(startCount + 1);
    }

    @Test
    @DisplayName("Возвращает ожидаемый список жанров")
    void getAll() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        List<Genre> actualGenreList = genreRepository.findAll();
        assertThat(actualGenreList).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(expectedGenre);
    }

    @Test
    @DisplayName("Находит жанр по id")
    void getById() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = genreRepository.getById(EXISTING_GENRE_ID);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("Не находит отсутствующий жанр")
    void getMissingById() {
        assertThat(genreRepository.findById(2L)).isEmpty();
    }

    @Test
    @DisplayName("Корректно меняет название жанра")
    void update() {
        String newGenreName = "New name";
        Genre existingGenre = genreRepository.findById(EXISTING_GENRE_ID).orElse(null);
        em.detach(existingGenre);
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, newGenreName);
        genreRepository.save(expectedGenre);
        em.detach(expectedGenre);
        Genre actualGenre = genreRepository.getById(EXISTING_GENRE_ID);
        assertThat(actualGenre)
                .isEqualTo(expectedGenre)
                .isNotEqualTo(existingGenre);
    }

    @Test
    @DisplayName("Корректно удаляет жанр")
    void deleteById() {
        Genre genre = new Genre(null, "New test genre");
        long newGenreId = genreRepository.save(genre).getId();

        long startCount = genreRepository.count();
        genreRepository.deleteById(newGenreId);
        assertThat(genreRepository.count()).isEqualTo(startCount - 1);
    }
}