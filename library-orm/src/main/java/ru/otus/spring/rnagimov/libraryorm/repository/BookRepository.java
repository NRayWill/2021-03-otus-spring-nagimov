package ru.otus.spring.rnagimov.libraryorm.repository;

import ru.otus.spring.rnagimov.libraryorm.dto.BookDto;

import java.util.List;

public interface BookRepository {

    long count();

    long insert(BookDto book);

    List<BookDto> getAll();

    BookDto getById(long id);

    int update(BookDto book);

    int deleteById(long id);
}
