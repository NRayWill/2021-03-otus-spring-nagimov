package ru.otus.spring.rnagimov.libraryorm.service;

import ru.otus.spring.rnagimov.libraryorm.dto.BookDto;
import ru.otus.spring.rnagimov.libraryorm.exception.AmbiguousElementDefinitionException;
import ru.otus.spring.rnagimov.libraryorm.exception.NoElementWithSuchIdException;

import java.util.List;

public interface BookService {
    long createBook(String title, long authorId, long genreId);

    List<BookDto> getAll();

    BookDto getById(long id) throws NoElementWithSuchIdException, AmbiguousElementDefinitionException;

    void updateBook(long id, String title, Long authorId, Long genreId);

    void deleteById(long id);
}
