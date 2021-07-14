package ru.otus.spring.rnagimov.libraryjdbc.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Author;

import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public long count() {
        return jdbc.getJdbcOperations().queryForObject("SELECT COUNT(1) FROM AUTHOR", Long.class);
    }

    @Override
    public long insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        params.addValue("surname", author.getSurname());
        params.addValue("middlename", author.getMiddleName());
        KeyHolder kh = new GeneratedKeyHolder();

        jdbc.update("INSERT INTO AUTHOR (name, surname, middlename) VALUES (:name, :surname, :middlename)", params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("SELECT * FROM AUTHOR", new AuthorMapper());
    }

    @Override
    public Author getById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        return jdbc.queryForObject("SELECT * FROM AUTHOR WHERE id = :id", params, new AuthorMapper());
    }

    @Override
    public void update(Author author) {
        final Map<String, Object> params = Map.of("id", author.getId(), "name", author.getName(), "surname", author.getSurname(), "middlename", author.getMiddleName());
        jdbc.update("UPDATE AUTHOR SET name = :name, surname = :surname, middlename = :middlename WHERE id = :id", params);
    }

    @Override
    public void deleteById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        jdbc.update("DELETE FROM AUTHOR WHERE id = :id", params);
    }
}
