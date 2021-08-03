package ru.otus.spring.rnagimov.libraryjpa.service;

import ru.otus.spring.rnagimov.libraryjpa.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> getAll();
}
