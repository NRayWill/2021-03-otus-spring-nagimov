package ru.otus.spring.rnagimov.libraryorm.service;

import ru.otus.spring.rnagimov.libraryorm.dto.CommentDto;
import ru.otus.spring.rnagimov.libraryorm.exception.AmbiguousElementDefinitionException;
import ru.otus.spring.rnagimov.libraryorm.exception.NoElementWithSuchIdException;

import java.util.List;

public interface CommentService {
    long createComment(Long bookId, String commentAuthor, String text) throws NoElementWithSuchIdException, AmbiguousElementDefinitionException;

    List<CommentDto> getAll();

    CommentDto getById(long id) throws NoElementWithSuchIdException, AmbiguousElementDefinitionException;

    List<CommentDto> getByBook(long bookId) throws NoElementWithSuchIdException, AmbiguousElementDefinitionException;

    List<CommentDto> getByCommentAuthor(String commentAuthor) throws NoElementWithSuchIdException;
}
