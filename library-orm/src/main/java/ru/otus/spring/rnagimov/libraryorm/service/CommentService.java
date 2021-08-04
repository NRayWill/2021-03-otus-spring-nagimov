package ru.otus.spring.rnagimov.libraryorm.service;

import ru.otus.spring.rnagimov.libraryorm.dto.CommentDto;
import ru.otus.spring.rnagimov.libraryorm.exception.NoElementWithSuchIdException;

import java.util.List;

public interface CommentService {
    long createComment(Long bookId, String commentAuthor, String text);

    List<CommentDto> getAll();

    CommentDto getById(long id);

    List<CommentDto> getByBook(long bookId) throws NoElementWithSuchIdException;

    List<CommentDto> getByCommentAuthor(String commentAuthor) throws NoElementWithSuchIdException;
}
