package ru.otus.spring.rnagimov.libraryjpa.service;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.rnagimov.libraryjpa.dto.CommentDto;
import ru.otus.spring.rnagimov.libraryjpa.exception.NoElementWithSuchIdException;
import ru.otus.spring.rnagimov.libraryjpa.mapper.CommonMapper;
import ru.otus.spring.rnagimov.libraryjpa.model.Book;
import ru.otus.spring.rnagimov.libraryjpa.model.Comment;
import ru.otus.spring.rnagimov.libraryjpa.repository.BookRepository;
import ru.otus.spring.rnagimov.libraryjpa.repository.CommentRepository;

import java.util.Date;
import java.util.List;

import static ru.otus.spring.rnagimov.libraryjpa.util.ConvertUtils.convertEntityListToDtoList;

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
    public long createComment(Long bookId, String commentAuthor, String text) throws NoElementWithSuchIdException {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoElementWithSuchIdException("Book doesn't exist"));
        Comment comment = new Comment(null, book, commentAuthor, text, new Date());
        return commentRepository.save(comment).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getAll() {
        return convertEntityListToDtoList(commentRepository.findAll(), CommentDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getById(long id) throws NoElementWithSuchIdException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NoElementWithSuchIdException(COMMENT_DOESNT_EXIST_MSG));
        return commentMapper.toDto(comment, CommentDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getByBook(long bookId) throws NoElementWithSuchIdException {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoElementWithSuchIdException("Book doesn't exist"));
        return convertEntityListToDtoList(commentRepository.findByBookOrderByCreatedDesc(book), CommentDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getByCommentAuthor(String commentAuthor) throws NoElementWithSuchIdException {
        List<Comment> commentList = commentRepository.findByCommentAuthorLikeIgnoreCaseOrderByCreatedDesc("%" + commentAuthor + "%");
        if (CollectionUtils.isEmpty(commentList)) {
            throw new NoElementWithSuchIdException(COMMENT_DOESNT_EXIST_MSG);
        }
        return convertEntityListToDtoList(commentList, CommentDto.class);
    }
}
