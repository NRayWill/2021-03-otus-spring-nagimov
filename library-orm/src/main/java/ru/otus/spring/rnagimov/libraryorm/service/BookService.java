package ru.otus.spring.rnagimov.libraryorm.service;

import ru.otus.spring.rnagimov.libraryorm.dto.BookDto;

import java.util.List;

public interface BookService {
    long createBook(String title, long authorId, long genreId);

    List<BookDto> getAll();

    BookDto getById(long id);

    void updateBook(long id, String title, Long authorId, Long genreId);

    void deleteById(long id);
}
