package ru.otus.spring.rnagimov.libraryorm.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.rnagimov.libraryorm.dto.BookDto;
import ru.otus.spring.rnagimov.libraryorm.dto.CommentDto;
import ru.otus.spring.rnagimov.libraryorm.exception.AmbiguousElementDefinitionException;
import ru.otus.spring.rnagimov.libraryorm.exception.NoElementWithSuchIdException;
import ru.otus.spring.rnagimov.libraryorm.repository.CommentRepository;

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
        BookDto book = bookService.getById(bookId);
        CommentDto commentDto = new CommentDto(null, book, commentAuthor, text, new Timestamp(new Date().getTime()));
        return commentRepository.insert(commentDto);
    }

    @Override
    public List<CommentDto> getAll() {
        return commentRepository.getAll();
    }

    @Override
    public CommentDto getById(long id) throws NoElementWithSuchIdException, AmbiguousElementDefinitionException {
        try {
            return commentRepository.getById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoElementWithSuchIdException(COMMENT_DOESNT_EXIST_MSG);
        } catch (IncorrectResultSizeDataAccessException ex) {
            throw new AmbiguousElementDefinitionException("There are more than one comment with id = " + id + " in database");
        }
    }

    @Override
    public List<CommentDto> getByBook(long bookId) throws NoElementWithSuchIdException, AmbiguousElementDefinitionException {
        BookDto book = bookService.getById(bookId);
        try {
            return commentRepository.getByBook(book);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoElementWithSuchIdException(COMMENT_DOESNT_EXIST_MSG);
        }
    }

    @Override
    public List<CommentDto> getByCommentAuthor(String commentAuthor) throws NoElementWithSuchIdException {
        try {
            return commentRepository.getByCommentAuthorLike(commentAuthor);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoElementWithSuchIdException(COMMENT_DOESNT_EXIST_MSG);
        }
    }
}
