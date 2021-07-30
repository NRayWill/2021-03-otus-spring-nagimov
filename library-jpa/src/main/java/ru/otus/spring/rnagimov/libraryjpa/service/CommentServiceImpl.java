package ru.otus.spring.rnagimov.libraryjpa.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.rnagimov.libraryjpa.exception.AmbiguousElementDefinitionException;
import ru.otus.spring.rnagimov.libraryjpa.exception.NoElementWithSuchIdException;
import ru.otus.spring.rnagimov.libraryjpa.model.Book;
import ru.otus.spring.rnagimov.libraryjpa.model.Comment;
import ru.otus.spring.rnagimov.libraryjpa.repository.CommentRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private static final String COMMENT_DOESNT_EXIST_MSG = "Comment doesn't exist";

    private final BookService bookService;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(BookService bookService, CommentRepository commentRepository) {
        this.bookService = bookService;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public long createComment(Long bookId, String commentAuthor, String text) throws NoElementWithSuchIdException, AmbiguousElementDefinitionException {
        Book book = bookService.getById(bookId);
        Comment commentDto = new Comment(null, commentAuthor, book, text, new Timestamp(new Date().getTime()));
        return commentRepository.save(commentDto).getId();
    }

    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getById(long id) throws NoElementWithSuchIdException, AmbiguousElementDefinitionException {
        try {
            return commentRepository.getById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoElementWithSuchIdException(COMMENT_DOESNT_EXIST_MSG);
        } catch (IncorrectResultSizeDataAccessException ex) {
            throw new AmbiguousElementDefinitionException("There are more than one comment with id = " + id + " in database");
        }
    }

    @Override
    public List<Comment> getByBook(long bookId) throws NoElementWithSuchIdException, AmbiguousElementDefinitionException {
        Book book = bookService.getById(bookId);
        try {
            return commentRepository.findByBookOrderByCreatedDesc(book);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoElementWithSuchIdException(COMMENT_DOESNT_EXIST_MSG);
        }
    }

    @Override
    public List<Comment> getByCommentAuthor(String commentAuthor) throws NoElementWithSuchIdException {
        try {
            return commentRepository.findByCommentAuthorLikeIgnoreCaseOrderByCreatedDesc("%" + commentAuthor + "%");
        } catch (EmptyResultDataAccessException ex) {
            throw new NoElementWithSuchIdException(COMMENT_DOESNT_EXIST_MSG);
        }
    }
}
