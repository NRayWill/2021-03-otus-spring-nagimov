package ru.otus.spring.rnagimov.libraryorm.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.rnagimov.libraryorm.model.Author;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static ru.otus.spring.rnagimov.libraryorm.repository.LibraryTestUtils.*;

@DataJpaTest
@Import(AuthorRepositoryImpl.class)
@DisplayName("Репозиторий AuthorRepository")
class AuthorRepositoryTest {

    @Autowired
    private TestEntityManager tem;
    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("Возвращает корректное количество записей")
    void count() {
        assertThat(authorRepository.count()).isEqualTo(getAuthorCount());
    }

    @Test
    @DisplayName("Добавляет автора без ошибок")
    void insert() {
        long startCount = getAuthorCount();
        Author author = new Author(null, "И", "О", "Ф");
        AtomicLong newId = new AtomicLong();
        assertThatCode(() -> newId.set(authorRepository.insert(author).getId())).doesNotThrowAnyException();
        assertThat(getAuthorCount()).isEqualTo(startCount + 1);
        assertThat(getTemItemById(tem, newId.get(), Author.class)).isEqualTo(author);
    }

    @Test
    @DisplayName("Возвращает ожидаемый список авторов")
    void getAll() {
        List<Author> actualAuthorList = authorRepository.getAll();
        assertThat(actualAuthorList).usingFieldByFieldElementComparator().containsExactlyInAnyOrder(getExistingAuthor());
    }

    @Test
    @DisplayName("Находит автора по id")
    void getById() {
        Author actualAuthor = authorRepository.getById(EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).isEqualTo(getExistingAuthor());
    }

    @Test
    @DisplayName("Корректно меняет ФИО автора")
    void update() {
        String newAuthorName = "New name";
        String newAuthorMiddleName = "New middle name";
        String newAuthorSurname = "New surname";
        Author existingAuthor = getExistingAuthor();
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, newAuthorName, newAuthorMiddleName, newAuthorSurname);
        authorRepository.update(expectedAuthor);
        Author actualAuthor = getExistingAuthor();
        assertThat(actualAuthor).isEqualTo(expectedAuthor).isNotEqualTo(existingAuthor);
    }

    @Test
    @DisplayName("Корректно удаляет автора")
    void deleteById() {
        Author author = new Author(null, "И", "О", "Ф");
        tem.persist(author);
        tem.flush();
        long newAuthorId = author.getId();

        long startCount = getAuthorCount();
        int deletedCount = authorRepository.deleteById(newAuthorId);
        assertThat(deletedCount).isEqualTo(1);
        assertThat(getAuthorCount()).isEqualTo(startCount - 1);
        assertThat(tem.getEntityManager().find(Author.class, EXISTING_AUTHOR_ID)).isNotEqualTo(author);
        assertThat(tem.getEntityManager().contains(author)).isFalse();
    }

    private long getAuthorCount() {
        return getTemCount(tem, Author.class);
    }

    private Author getExistingAuthor() {
        return getTemItemById(tem, LibraryTestUtils.EXISTING_AUTHOR_ID, Author.class);
    }
}