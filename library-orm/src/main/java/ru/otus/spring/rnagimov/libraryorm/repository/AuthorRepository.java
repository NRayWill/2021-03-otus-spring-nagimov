package ru.otus.spring.rnagimov.libraryorm.repository;

import ru.otus.spring.rnagimov.libraryorm.dto.AuthorDto;

import java.util.List;

public interface AuthorRepository {

    long count();

    long insert(AuthorDto author);

    List<AuthorDto> getAll();

    AuthorDto getById(long id);

    int update(AuthorDto author);

    int deleteById(long id);
}
