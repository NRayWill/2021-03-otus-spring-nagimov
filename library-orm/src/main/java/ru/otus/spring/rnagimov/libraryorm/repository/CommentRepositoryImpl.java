package ru.otus.spring.rnagimov.libraryorm.repository;

import org.springframework.stereotype.Repository;
import ru.otus.spring.rnagimov.libraryorm.model.Author;
import ru.otus.spring.rnagimov.libraryorm.model.Book;
import ru.otus.spring.rnagimov.libraryorm.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Locale;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    public CommentRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public long count() {
        return em.createQuery("select count(c.id) from Comment c", Long.class).getSingleResult();
    }

    @Override
    public Comment insert(Comment comment) {
        em.persist(comment);
        em.flush();
        return comment;
    }

    @Override
    public List<Comment> getAll() {
        return em.createQuery("select c from Comment c", Comment.class).getResultList();
    }

    @Override
    public Comment getById(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> getByBook(Book book) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book = :book order by c.created desc", Comment.class);
        query.setParameter("book", book);
        return query.getResultList();
    }

    @Override
    public List<Comment> getByCommentAuthorLike(String commentAuthor) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where upper(c.commentAuthor) like :commentAuthor order by c.created desc", Comment.class);
        query.setParameter("commentAuthor", "%" + commentAuthor.toUpperCase(Locale.ROOT) + "%");
        return query.getResultList();
    }

    @Override
    public void update(Comment comment) {
        em.merge(comment);
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Comment a where a.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
