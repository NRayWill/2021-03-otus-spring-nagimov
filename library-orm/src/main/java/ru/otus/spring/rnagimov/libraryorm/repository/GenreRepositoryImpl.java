package ru.otus.spring.rnagimov.libraryorm.repository;

import org.springframework.stereotype.Repository;
import ru.otus.spring.rnagimov.libraryorm.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class GenreRepositoryImpl implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    public GenreRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public long count() {
        return em.createQuery("select count(g.id) from Genre g", Long.class).getSingleResult();
    }

    @Override
    public Genre insert(Genre genre) {
        em.persist(genre);
        em.flush();
        return genre;
    }

    @Override
    public List<Genre> getAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    @Override
    public Genre getById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public void update(Genre genre) {
        em.merge(genre);
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
