package ru.otus.spring.rnagimov.libraryjdbc.service;

import ru.otus.spring.rnagimov.libraryjdbc.exception.AmbiguousRowDefinitionException;
import ru.otus.spring.rnagimov.libraryjdbc.exception.RowAlreadyExistsException;
import ru.otus.spring.rnagimov.libraryjdbc.exception.NoSuchRowException;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Long> createBook(Long id, String title, long authorId, long genreId) throws RowAlreadyExistsException;

    List<Book> getAll();

    Book getById(long id) throws NoSuchRowException, AmbiguousRowDefinitionException;

    void updateBook(long id, String title, Long authorId, Long genreId);

    void deleteById(long id);
}
