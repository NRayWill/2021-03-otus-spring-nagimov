package ru.otus.spring.rnagimov.libraryorm.repository;

import ru.otus.spring.rnagimov.libraryorm.model.Book;
import ru.otus.spring.rnagimov.libraryorm.model.Comment;

import java.util.List;

public interface CommentRepository {

    long count();

    long insert(Comment comment);

    List<Comment> getAll();

    Comment getById(long id);

    List<Comment> getByBook(Book book);

    List<Comment> getByCommentAuthorLike(String commentAuthor);

    void update(Comment author);

    int deleteById(long id);
}
