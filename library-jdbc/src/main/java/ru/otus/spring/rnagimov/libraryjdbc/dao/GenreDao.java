package ru.otus.spring.rnagimov.libraryjdbc.dao;

import ru.otus.spring.rnagimov.libraryjdbc.domain.Genre;

import java.util.List;

public interface GenreDao {

    long count();

    long insert(Genre genre);

    List<Genre> getAll();

    Genre getById(long id);

    void update(Genre genre);

    void deleteById(long id);
}
