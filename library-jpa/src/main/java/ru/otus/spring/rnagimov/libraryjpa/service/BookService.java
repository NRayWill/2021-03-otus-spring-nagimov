package ru.otus.spring.rnagimov.libraryjpa.service;

import ru.otus.spring.rnagimov.libraryjpa.dto.BookDto;
import ru.otus.spring.rnagimov.libraryjpa.exception.NoElementWithSuchIdException;

import java.util.List;

public interface BookService {
    long createBook(String title, long authorId, long genreId) throws NoElementWithSuchIdException;

    List<BookDto> getAll();

    BookDto getById(long id) throws NoElementWithSuchIdException;

    void updateBook(long id, String title, Long authorId, Long genreId) throws NoElementWithSuchIdException;

    void deleteById(long id);
}
