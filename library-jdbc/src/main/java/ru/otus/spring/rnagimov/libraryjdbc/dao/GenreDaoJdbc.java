package ru.otus.spring.rnagimov.libraryjdbc.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Genre;

import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public long count() {
        return jdbc.getJdbcOperations().queryForObject("SELECT COUNT(1) FROM GENRE", Long.class);
    }

    @Override
    public long insert(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());
        KeyHolder kh = new GeneratedKeyHolder();

        jdbc.update("INSERT INTO GENRE (name) VALUES (:name)", params, kh);
        return kh.getKey().longValue();
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("SELECT ID, NAME FROM GENRE", new GenreMapper());
    }

    @Override
    public Genre getById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        return jdbc.queryForObject("SELECT ID, NAME FROM GENRE WHERE id = :id", params, new GenreMapper());
    }

    @Override
    public void update(Genre genre) {
        final Map<String, Object> params = Map.of("id", genre.getId(), "name", genre.getName());
        jdbc.update("UPDATE GENRE SET name = :name WHERE id = :id", params);
    }

    @Override
    public void deleteById(long id) {
        final Map<String, Object> params = Map.of("id", id);
        jdbc.update("DELETE FROM GENRE WHERE id = :id", params);
    }
}
