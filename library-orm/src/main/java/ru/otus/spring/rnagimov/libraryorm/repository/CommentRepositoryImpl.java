package ru.otus.spring.rnagimov.libraryorm.repository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import ru.otus.spring.rnagimov.libraryorm.dto.BookDto;
import ru.otus.spring.rnagimov.libraryorm.dto.CommentDto;
import ru.otus.spring.rnagimov.libraryorm.mapper.CommonMapper;
import ru.otus.spring.rnagimov.libraryorm.model.Book;
import ru.otus.spring.rnagimov.libraryorm.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    private final CommonMapper<Comment, CommentDto> commentMapper;
    private final CommonMapper<Book, BookDto> bookMapper;

    public CommentRepositoryImpl(EntityManager em, ModelMapper modelMapper) {
        this.em = em;
        this.commentMapper = new CommonMapper<>(modelMapper);
        this.bookMapper = new CommonMapper<>(modelMapper);
    }

    @Override
    public long count() {
        return em.createQuery("select count(c.id) from Comment c", Long.class).getSingleResult();
    }

    @Override
    public long insert(CommentDto comment) {
        Comment commentForInsert = commentMapper.toEntity(comment, Comment.class);
        em.persist(commentForInsert);
        return commentForInsert.getId();
    }

    @Override
    public List<CommentDto> getAll() {
        List<Comment> commentList = em.createQuery("select c from Comment c", Comment.class).getResultList();
        return commentList.stream().map(e -> {
            em.detach(e);
            return commentMapper.toDto(e, CommentDto.class);
        }).collect(Collectors.toList());
    }

    @Override
    public CommentDto getById(long id) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.id = :id", Comment.class);
        query.setParameter("id", id);
        Comment comment = query.getSingleResult();
        em.detach(comment);
        return commentMapper.toDto(comment, CommentDto.class);
    }

    @Override
    public List<CommentDto> getByBook(BookDto bookDto) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book = :book order by c.created desc", Comment.class);
        query.setParameter("book", bookMapper.toEntity(bookDto, Book.class));
        List<Comment> commentList = query.getResultList();
        return commentList.stream().map(e -> {
            em.detach(e);
            return commentMapper.toDto(e, CommentDto.class);
        }).collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getByCommentAuthorLike(String commentAuthor) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where upper(c.commentAuthor) like :commentAuthor order by c.created desc", Comment.class);
        query.setParameter("commentAuthor", "%" + commentAuthor.toUpperCase(Locale.ROOT) + "%");
        List<Comment> commentList = query.getResultList();
        return commentList.stream().map(e -> {
            em.detach(e);
            return commentMapper.toDto(e, CommentDto.class);
        }).collect(Collectors.toList());
    }

    @Override
    public int update(CommentDto comment) {
        Query query = em.createQuery("update Comment a set a.book = :book, a.commentAuthor = :commentAuthor, a.text = :text where a.id = :id");
        query.setParameter("id", comment.getId());
        query.setParameter("book", bookMapper.toEntity(comment.getBook(), Book.class));
        query.setParameter("commentAuthor", comment.getCommentAuthor());
        query.setParameter("text", comment.getText());
        return query.executeUpdate();
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Comment a where a.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
