package ru.otus.spring.rnagimov.libraryorm.repository;

import ru.otus.spring.rnagimov.libraryorm.dto.BookDto;
import ru.otus.spring.rnagimov.libraryorm.dto.CommentDto;

import java.util.List;

public interface CommentRepository {

    long count();

    long insert(CommentDto comment);

    List<CommentDto> getAll();

    CommentDto getById(long id);

    List<CommentDto> getByBook(BookDto bookDto);

    List<CommentDto> getByCommentAuthorLike(String commentAuthor);

    int update(CommentDto author);

    int deleteById(long id);
}
