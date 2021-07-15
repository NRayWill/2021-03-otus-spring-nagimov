package ru.otus.spring.rnagimov.libraryjdbc.service;

import ru.otus.spring.rnagimov.libraryjdbc.domain.Book;
import ru.otus.spring.rnagimov.libraryjdbc.exception.AmbiguousElementDefinitionException;
import ru.otus.spring.rnagimov.libraryjdbc.exception.NoSuchElementException;

import java.util.List;

public interface BookService {
    long createBook(String title, long authorId, long genreId);

    List<Book> getAll();

    Book getById(long id) throws NoSuchElementException, AmbiguousElementDefinitionException;

    void updateBook(long id, String title, Long authorId, Long genreId);

    void deleteById(long id);
}
