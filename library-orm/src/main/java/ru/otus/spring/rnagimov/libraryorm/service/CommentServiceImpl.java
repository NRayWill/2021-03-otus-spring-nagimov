package ru.otus.spring.rnagimov.libraryorm.service;

import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.rnagimov.libraryorm.dto.CommentDto;
import ru.otus.spring.rnagimov.libraryorm.exception.NoElementWithSuchIdException;
import ru.otus.spring.rnagimov.libraryorm.mapper.CommonMapper;
import ru.otus.spring.rnagimov.libraryorm.model.Book;
import ru.otus.spring.rnagimov.libraryorm.model.Comment;
import ru.otus.spring.rnagimov.libraryorm.repository.BookRepository;
import ru.otus.spring.rnagimov.libraryorm.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static ru.otus.spring.rnagimov.libraryorm.util.ConvertUtils.convertEntityListToDtoList;

@Service
public class CommentServiceImpl implements CommentService {

    private static final String COMMENT_DOESNT_EXIST_MSG = "Comment doesn't exist";

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final CommonMapper<Comment, CommentDto> commentMapper;

    public CommentServiceImpl(BookRepository bookRepository, CommentRepository commentRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.commentMapper = new CommonMapper<>(modelMapper);
    }

    @Override
    @Transactional
    public long createComment(Long bookId, String commentAuthor, String text) {
        Book book = bookRepository.getById(bookId);
        Comment comment = new Comment(null, book, commentAuthor, text, new Date());
        return commentRepository.insert(comment);
    }

    @Override
    @Transactional
    public List<CommentDto> getAll() {
        return convertEntityListToDtoList(commentRepository.getAll(), CommentDto.class);
    }

    @Override
    @Transactional
    public CommentDto getById(long id) {
        return commentMapper.toDto(commentRepository.getById(id), CommentDto.class);
    }

    @Override
    public List<CommentDto> getByBook(long bookId) throws NoElementWithSuchIdException {
        Book book = bookRepository.getById(bookId);
        if (book == null) {
            throw new NoElementWithSuchIdException("Book doesn't exist");
        }
        return convertEntityListToDtoList(commentRepository.getByBook(book), CommentDto.class);
    }

    @Override
    public List<CommentDto> getByCommentAuthor(String commentAuthor) throws NoElementWithSuchIdException {
        try {
            return convertEntityListToDtoList(commentRepository.getByCommentAuthorLike(commentAuthor), CommentDto.class);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoElementWithSuchIdException(COMMENT_DOESNT_EXIST_MSG);
        }
    }
}
