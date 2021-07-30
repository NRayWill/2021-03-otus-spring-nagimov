package ru.otus.spring.rnagimov.libraryorm.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.rnagimov.libraryorm.dto.AuthorDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.otus.spring.rnagimov.libraryorm.repository.LibraryTestUtils.*;

@DataJpaTest
@Import(AuthorRepositoryImpl.class)
@DisplayName("Репозиторий AuthorRepository")
class AuthorRepositoryTest {

    public static final int INIT_AUTHOR_COUNT = 1;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Возвращает корректное количество записей")
    void count() {
        assertThat(authorRepository.count()).isEqualTo(INIT_AUTHOR_COUNT);
    }

    @Test
    @DisplayName("Добавляет автора без ошибок")
    void insert() {
        long startCount = authorRepository.count();
        AuthorDto author = new AuthorDto(null, "И", "О", "Ф");
        assertThatCode(() -> authorRepository.insert(author)).doesNotThrowAnyException();
        assertThat(authorRepository.count()).isEqualTo(startCount + 1);
    }

    @Test
    @DisplayName("Возвращает ожидаемый список авторов")
    void getAll() {
        AuthorDto expectedAuthor = new AuthorDto(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        List<AuthorDto> actualAuthorList = authorRepository.getAll();
        assertThat(actualAuthorList).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(expectedAuthor);
    }

    @Test
    @DisplayName("Находит автора по id")
    void getById() {
        AuthorDto expectedAuthor = new AuthorDto(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        AuthorDto actualAuthor = authorRepository.getById(EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }
    @Test
    @DisplayName("Корректно меняет ФИО автора")
    void update() {
        String newAuthorName = "New name";
        String newAuthorMiddleName = "New middle name";
        String newAuthorSurname = "New surname";
        AuthorDto existingAuthor = authorRepository.getById(EXISTING_AUTHOR_ID);
        AuthorDto expectedAuthor = new AuthorDto(EXISTING_AUTHOR_ID, newAuthorName, newAuthorMiddleName, newAuthorSurname);
        int updatedCount = authorRepository.update(expectedAuthor);
        assertThat(updatedCount).isEqualTo(1);
        AuthorDto actualAuthor = authorRepository.getById(EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).isEqualTo(expectedAuthor).isNotEqualTo(existingAuthor);
    }

    @Test
    @DisplayName("Корректно удаляет автора")
    void deleteById() {
        AuthorDto author = new AuthorDto(null, "И", "О", "Ф");
        long newAuthorId = authorRepository.insert(author);

        long startCount = authorRepository.count();
        int deletedCount = authorRepository.deleteById(newAuthorId);
        assertThat(deletedCount).isEqualTo(1);
        assertThat(authorRepository.count()).isEqualTo(startCount - 1);
    }
}