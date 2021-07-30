package ru.otus.spring.rnagimov.libraryjpa.service;

import ru.otus.spring.rnagimov.libraryjpa.exception.AmbiguousElementDefinitionException;
import ru.otus.spring.rnagimov.libraryjpa.exception.NoElementWithSuchIdException;
import ru.otus.spring.rnagimov.libraryjpa.model.Comment;

import java.util.List;

public interface CommentService {
    long createComment(Long bookId, String commentAuthor, String text) throws NoElementWithSuchIdException, AmbiguousElementDefinitionException;

    List<Comment> getAll();

    Comment getById(long id) throws NoElementWithSuchIdException, AmbiguousElementDefinitionException;

    List<Comment> getByBook(long bookId) throws NoElementWithSuchIdException, AmbiguousElementDefinitionException;

    List<Comment> getByCommentAuthor(String commentAuthor) throws NoElementWithSuchIdException;
}
