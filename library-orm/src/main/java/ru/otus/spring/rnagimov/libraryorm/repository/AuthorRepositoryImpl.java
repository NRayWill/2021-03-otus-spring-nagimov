package ru.otus.spring.rnagimov.libraryorm.repository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import ru.otus.spring.rnagimov.libraryorm.dto.AuthorDto;
import ru.otus.spring.rnagimov.libraryorm.mapper.CommonMapper;
import ru.otus.spring.rnagimov.libraryorm.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    private final CommonMapper<Author, AuthorDto> mapper;

    public AuthorRepositoryImpl(EntityManager em, ModelMapper modelMapper) {
        this.em = em;
        this.mapper = new CommonMapper<>(modelMapper);
    }

    @Override
    public long count() {
        return em.createQuery("select count(a.id) from Author a", Long.class).getSingleResult();
    }

    @Override
    public long insert(AuthorDto author) {
        Author authorForInsert = mapper.toEntity(author, Author.class);
        em.persist(authorForInsert);
        return authorForInsert.getId();
    }

    @Override
    public List<AuthorDto> getAll() {
        List<Author> authorList = em.createQuery("select a from Author a", Author.class).getResultList();
        return authorList.stream().map(e -> {
            em.detach(e);
            return mapper.toDto(e, AuthorDto.class);
        }).collect(Collectors.toList());
    }

    @Override
    public AuthorDto getById(long id) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.id = :id", Author.class);
        query.setParameter("id", id);
        Author author = query.getSingleResult();
        em.detach(author);
        return mapper.toDto(author, AuthorDto.class);
    }

    @Override
    public int update(AuthorDto author) {
        Query query = em.createQuery("update Author a set a.name = :name, a.surname = :surname, a.middleName = :middleName where a.id = :id");
        query.setParameter("id", author.getId());
        query.setParameter("name", author.getName());
        query.setParameter("surname", author.getSurname());
        query.setParameter("middleName", author.getMiddleName());
        return query.executeUpdate();
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
