package ru.otus.spring.rnagimov.libraryorm.service;

import ru.otus.spring.rnagimov.libraryorm.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> getAll();
}
