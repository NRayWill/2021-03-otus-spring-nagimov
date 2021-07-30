package ru.otus.spring.rnagimov.libraryorm.repository;

import ru.otus.spring.rnagimov.libraryorm.dto.GenreDto;

import java.util.List;

public interface GenreRepository {

    long count();

    long insert(GenreDto genre);

    List<GenreDto> getAll();

    GenreDto getById(long id);

    int update(GenreDto genre);

    int deleteById(long id);
}
