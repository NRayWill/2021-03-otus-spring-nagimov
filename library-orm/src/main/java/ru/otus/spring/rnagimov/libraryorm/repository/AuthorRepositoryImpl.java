package ru.otus.spring.rnagimov.libraryorm.repository;

import org.springframework.stereotype.Repository;
import ru.otus.spring.rnagimov.libraryorm.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    public AuthorRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public long count() {
        return em.createQuery("select count(a.id) from Author a", Long.class).getSingleResult();
    }

    @Override
    public Author insert(Author author) {
        em.persist(author);
        return author;
    }

    @Override
    public List<Author> getAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public Author getById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    public void update(Author author) {
        em.merge(author);
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
