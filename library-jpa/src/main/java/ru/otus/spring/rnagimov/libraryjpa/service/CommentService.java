package ru.otus.spring.rnagimov.libraryjpa.service;

import ru.otus.spring.rnagimov.libraryjpa.dto.CommentDto;
import ru.otus.spring.rnagimov.libraryjpa.exception.NoElementWithSuchIdException;

import java.util.List;

public interface CommentService {
    long createComment(Long bookId, String commentAuthor, String text) throws NoElementWithSuchIdException;

    List<CommentDto> getAll();

    CommentDto getById(long id) throws NoElementWithSuchIdException;

    List<CommentDto> getByBook(long bookId) throws NoElementWithSuchIdException;

    List<CommentDto> getByCommentAuthor(String commentAuthor) throws NoElementWithSuchIdException;
}
