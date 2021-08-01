package ru.otus.spring.rnagimov.libraryorm.repository;

import org.springframework.stereotype.Repository;
import ru.otus.spring.rnagimov.libraryorm.model.Book;

import javax.persistence.*;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {


    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public long count() {
        return em.createQuery("select count(b.id) from Book b", Long.class).getSingleResult();
    }

    @Override
    public long insert(Book book) {
        em.persist(book);
        em.flush();
        return book.getId();
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-author-genre-entity-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        List<Book> bookList = query.getResultList();
        bookList.forEach(em::detach);
        return bookList;
    }

    @Override
    public Book getById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
