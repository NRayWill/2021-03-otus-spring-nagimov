package ru.otus.spring.rnagimov.libraryorm.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.rnagimov.libraryorm.dto.GenreDto;

import javax.persistence.NoResultException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static ru.otus.spring.rnagimov.libraryorm.repository.LibraryTestUtils.*;

@DataJpaTest
@Import(GenreRepositoryImpl.class)
@DisplayName("Репозиторий GenreRepository")
class GenreRepositoryTest {

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
        GenreDto genre = new GenreDto(null, "New test genre");
        assertThatCode(() -> genreRepository.insert(genre)).doesNotThrowAnyException();
        assertThat(genreRepository.count()).isEqualTo(startCount + 1);
    }

    @Test
    @DisplayName("Возвращает ожидаемый список жанров")
    void getAll() {
        GenreDto expectedGenre = new GenreDto(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        List<GenreDto> actualGenreList = genreRepository.getAll();
        assertThat(actualGenreList).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(expectedGenre);
    }

    @Test
    @DisplayName("Находит жанр по id")
    void getById() {
        GenreDto expectedGenre = new GenreDto(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        GenreDto actualGenre = genreRepository.getById(EXISTING_GENRE_ID);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("Не находит отсутствующий жанр")
    void getMissingById() {
        assertThatThrownBy(() -> genreRepository.getById(2)).isInstanceOf(NoResultException.class);
    }

    @Test
    @DisplayName("Корректно меняет название жанра")
    void update() {
        String newGenreName = "New name";
        GenreDto existingGenre = genreRepository.getById(EXISTING_GENRE_ID);
        GenreDto expectedGenre = new GenreDto(EXISTING_GENRE_ID, newGenreName);
        int updatedCount = genreRepository.update(expectedGenre);
        assertThat(updatedCount).isEqualTo(1);
        GenreDto actualGenre = genreRepository.getById(EXISTING_GENRE_ID);
        assertThat(actualGenre).isEqualTo(expectedGenre).isNotEqualTo(existingGenre);
    }

    @Test
    @DisplayName("Корректно удаляет жанр")
    void deleteById() {
        GenreDto genre = new GenreDto(null, "New test genre");
        long newGenreId = genreRepository.insert(genre);

        long startCount = genreRepository.count();
        int deletedCount = genreRepository.deleteById(newGenreId);
        assertThat(deletedCount).isEqualTo(1);
        assertThat(genreRepository.count()).isEqualTo(startCount - 1);
    }
}