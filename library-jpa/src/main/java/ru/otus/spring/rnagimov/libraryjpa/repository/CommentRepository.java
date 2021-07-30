package ru.otus.spring.rnagimov.libraryjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.rnagimov.libraryjpa.model.Book;
import ru.otus.spring.rnagimov.libraryjpa.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBookOrderByCreatedDesc(Book book);

    List<Comment> findByCommentAuthorLikeIgnoreCaseOrderByCreatedDesc(String commentAuthor);
}
