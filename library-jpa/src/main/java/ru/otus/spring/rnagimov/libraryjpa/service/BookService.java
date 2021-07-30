package ru.otus.spring.rnagimov.libraryjpa.service;

import ru.otus.spring.rnagimov.libraryjpa.exception.AmbiguousElementDefinitionException;
import ru.otus.spring.rnagimov.libraryjpa.exception.NoElementWithSuchIdException;
import ru.otus.spring.rnagimov.libraryjpa.model.Book;

import java.util.List;

public interface BookService {
    long createBook(String title, long authorId, long genreId);

    List<Book> getAll();

    Book getById(long id) throws NoElementWithSuchIdException, AmbiguousElementDefinitionException;

    void updateBook(long id, String title, Long authorId, Long genreId);

    void deleteById(long id);
}
