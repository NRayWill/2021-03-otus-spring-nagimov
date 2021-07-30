package ru.otus.spring.rnagimov.libraryjdbc.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public long count() {
        return jdbc.getJdbcOperations().queryForObject("SELECT COUNT(1) FROM BOOK", Long.class);
    }

    @Override
    public long insert(Book book) {
        final Map<String, Object> paramsMap = new HashMap<>(Map.of(
                "title", book.getTitle(),
                "authorId", book.getAuthor().getId(),
                "genreId", book.getGenre().getId()));

        MapSqlParameterSource params = new MapSqlParameterSource(paramsMap);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update("INSERT INTO BOOK (TITLE, AUTHOR_ID, GENRE_ID) VALUES (:title, :authorId, :genreId)", params, keyHolder, new String[]{"ID"});
        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("SELECT BOOK.ID, TITLE, GENRE_ID, AUTHOR_ID, GENRE.ID, GENRE.NAME, AUTHOR.ID, AUTHOR.NAME, AUTHOR.SURNAME, AUTHOR.MIDDLENAME " +
                "FROM BOOK " +
                "LEFT JOIN AUTHOR ON AUTHOR.ID = BOOK.AUTHOR_ID " +
                "LEFT JOIN GENRE ON GENRE.ID = BOOK.GENRE_ID", new BookMapper());
    }

    @Override
    public Book getById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        return jdbc.queryForObject("SELECT BOOK.ID, TITLE, GENRE_ID, AUTHOR_ID, GENRE.ID, GENRE.NAME, AUTHOR.ID, AUTHOR.NAME, AUTHOR.SURNAME, AUTHOR.MIDDLENAME " +
                "FROM BOOK " +
                "LEFT JOIN AUTHOR ON AUTHOR.ID = BOOK.AUTHOR_ID " +
                "LEFT JOIN GENRE ON GENRE.ID = BOOK.GENRE_ID " +
                "WHERE BOOK.ID = :id", params, new BookMapper());
    }

    @Override
    public void update(Book book) {
        final Map<String, Object> params = Map.of(
                "id", book.getId(),
                "title", book.getTitle(),
                "authorId", book.getAuthor().getId(),
                "genreId", book.getGenre().getId());
        jdbc.update("UPDATE BOOK SET TITLE = :title, AUTHOR_ID = :authorId, GENRE_ID = :genreId WHERE id = :id", params);
    }

    @Override
    public void deleteById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        jdbc.update("DELETE FROM BOOK WHERE ID = :id", params);
    }
}
