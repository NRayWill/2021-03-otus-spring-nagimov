package ru.otus.spring.rnagimov.libraryorm.repository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import ru.otus.spring.rnagimov.libraryorm.dto.GenreDto;
import ru.otus.spring.rnagimov.libraryorm.mapper.CommonMapper;
import ru.otus.spring.rnagimov.libraryorm.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GenreRepositoryImpl implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    private final CommonMapper<Genre, GenreDto> mapper;

    public GenreRepositoryImpl(EntityManager em, ModelMapper modelMapper) {
        this.em = em;
        this.mapper = new CommonMapper<>(modelMapper);
    }

    @Override
    public long count() {
        return em.createQuery("select count(g.id) from Genre g", Long.class).getSingleResult();
    }

    @Override
    public long insert(GenreDto genre) {
        Genre genreForInsert = mapper.toEntity(genre, Genre.class);
        em.persist(genreForInsert);
        return genreForInsert.getId();
    }

    @Override
    public List<GenreDto> getAll() {
        List<Genre> genreList = em.createQuery("select g from Genre g", Genre.class).getResultList();
        return genreList.stream().map(e -> {
            em.detach(e);
            return mapper.toDto(e, GenreDto.class);
        }).collect(Collectors.toList());
    }

    @Override
    public GenreDto getById(long id) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.id = :id", Genre.class);
        query.setParameter("id", id);
        Genre genre = query.getSingleResult();
        em.detach(genre);
        return mapper.toDto(genre, GenreDto.class);
    }

    @Override
    public int update(GenreDto genre) {
        Query query = em.createQuery("update Genre g set g.name = :name where g.id = :id");
        query.setParameter("id", genre.getId());
        query.setParameter("name", genre.getName());
        return query.executeUpdate();
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
