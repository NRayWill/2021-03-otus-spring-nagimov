package ru.otus.spring.rnagimov.libraryjpa.service;

import ru.otus.spring.rnagimov.libraryjpa.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> getAll();
}
