package ru.otus.spring.rnagimov.libraryjpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.rnagimov.libraryjpa.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.otus.spring.rnagimov.libraryjpa.repository.LibraryTestUtils.*;

@DataJpaTest
@DisplayName("Репозиторий AuthorRepository")
class AuthorRepositoryTest {

    @PersistenceContext
    private EntityManager em;

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
        Author author = new Author(null, "И", "О", "Ф");
        assertThatCode(() -> authorRepository.save(author)).doesNotThrowAnyException();
        assertThat(authorRepository.count()).isEqualTo(startCount + 1);
    }

    @Test
    @DisplayName("Возвращает ожидаемый список авторов")
    void getAll() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        List<Author> actualAuthorList = authorRepository.findAll();
        assertThat(actualAuthorList).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(expectedAuthor);
    }

    @Test
    @DisplayName("Находит автора по id")
    void getById() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        Author actualAuthor = authorRepository.getById(EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }
    @Test
    @DisplayName("Корректно меняет ФИО автора")
    void update() {
        String newAuthorName = "New name";
        String newAuthorMiddleName = "New middle name";
        String newAuthorSurname = "New surname";
        Author existingAuthor = authorRepository.findById(EXISTING_AUTHOR_ID).orElse(null);
        em.detach(existingAuthor);
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, newAuthorName, newAuthorMiddleName, newAuthorSurname);
        authorRepository.save(expectedAuthor);
        em.detach(expectedAuthor);
        Author actualAuthor = authorRepository.getById(EXISTING_AUTHOR_ID);
        assertThat(actualAuthor)
                .isEqualTo(expectedAuthor)
                .isNotEqualTo(existingAuthor);
    }

    @Test
    @DisplayName("Корректно удаляет автора")
    void deleteById() {
        Author author = new Author(null, "И", "О", "Ф");
        long newAuthorId = authorRepository.save(author).getId();

        long startCount = authorRepository.count();
        authorRepository.deleteById(newAuthorId);
        assertThat(authorRepository.count()).isEqualTo(startCount - 1);
    }
}