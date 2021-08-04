package ru.otus.spring.rnagimov.libraryjpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.rnagimov.libraryjpa.model.Genre;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.otus.spring.rnagimov.libraryjpa.repository.LibraryTestUtils.*;

@DataJpaTest
@DisplayName("Репозиторий GenreRepository")
class GenreRepositoryTest {

    @Autowired
    private TestEntityManager tem;
    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Возвращает корректное количество записей")
    void count() {
        assertThat(genreRepository.count()).isEqualTo(getGenreCount());
    }

    @Test
    @DisplayName("Добавляет жанр без ошибок")
    void insert() {
        long startCount = getGenreCount();
        Genre genre = new Genre(null, "New test genre");
        AtomicLong newId = new AtomicLong();
        assertThatCode(() -> newId.set(genreRepository.save(genre).getId())).doesNotThrowAnyException();
        assertThat(getGenreCount()).isEqualTo(startCount + 1);
        assertThat(getTemItemById(tem, newId.get(), Genre.class)).isEqualTo(genre);
    }

    @Test
    @DisplayName("Возвращает ожидаемый список жанров")
    void getAll() {
        Genre expectedGenre = getExistingGenre();
        List<Genre> actualGenreList = genreRepository.findAll();
        assertThat(actualGenreList).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(expectedGenre);
    }

    @Test
    @DisplayName("Находит жанр по id")
    void getById() {
        Genre expectedGenre = getExistingGenre();
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
        Genre existingGenre = getExistingGenre();
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, newGenreName);
        genreRepository.save(expectedGenre);
        Genre actualGenre = getExistingGenre();
        assertThat(actualGenre).isEqualTo(expectedGenre).isNotEqualTo(existingGenre);
    }

    @Test
    @DisplayName("Корректно удаляет жанр")
    void deleteById() {
        Genre genre = new Genre(null, "New test genre");
        tem.persist(genre);
        tem.flush();
        long newGenreId = genre.getId();

        long startCount = getGenreCount();
        genreRepository.deleteById(newGenreId);
        assertThat(getGenreCount()).isEqualTo(startCount - 1);
        assertThat(tem.getEntityManager().find(Genre.class, EXISTING_GENRE_ID)).isNotEqualTo(genre);
        assertThat(tem.getEntityManager().contains(genre)).isFalse();
    }

    private long getGenreCount() {
        return getTemCount(tem, Genre.class);
    }

    private Genre getExistingGenre() {
        return getTemItemById(tem, LibraryTestUtils.EXISTING_GENRE_ID, Genre.class);
    }
}