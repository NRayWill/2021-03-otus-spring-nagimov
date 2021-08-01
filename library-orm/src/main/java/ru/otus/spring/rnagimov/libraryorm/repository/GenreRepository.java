package ru.otus.spring.rnagimov.libraryorm.repository;

import ru.otus.spring.rnagimov.libraryorm.model.Genre;

import java.util.List;

public interface GenreRepository {

    long count();

    long insert(Genre genre);

    List<Genre> getAll();

    Genre getById(long id);

    void update(Genre genre);

    int deleteById(long id);
}
