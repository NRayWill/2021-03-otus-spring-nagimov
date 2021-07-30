package ru.otus.spring.rnagimov.libraryorm.repository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import ru.otus.spring.rnagimov.libraryorm.dto.AuthorDto;
import ru.otus.spring.rnagimov.libraryorm.dto.BookDto;
import ru.otus.spring.rnagimov.libraryorm.dto.GenreDto;
import ru.otus.spring.rnagimov.libraryorm.mapper.CommonMapper;
import ru.otus.spring.rnagimov.libraryorm.model.Author;
import ru.otus.spring.rnagimov.libraryorm.model.Book;
import ru.otus.spring.rnagimov.libraryorm.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BookRepositoryImpl implements BookRepository {


    @PersistenceContext
    private final EntityManager em;

    private final CommonMapper<Book, BookDto> bookMapper;
    private final CommonMapper<Author, AuthorDto> authorMapper;
    private final CommonMapper<Genre, GenreDto> genreMapper;

    public BookRepositoryImpl(EntityManager em, ModelMapper modelMapper) {
        this.em = em;
        this.bookMapper = new CommonMapper<>(modelMapper);
        this.authorMapper = new CommonMapper<>(modelMapper);
        this.genreMapper = new CommonMapper<>(modelMapper);
    }

    @Override
    public long count() {
        return em.createQuery("select count(b.id) from Book b", Long.class).getSingleResult();
    }

    @Override
    public long insert(BookDto book) {
        Book bookForInsert = bookMapper.toEntity(book, Book.class);
        em.persist(bookForInsert);
        return bookForInsert.getId();
    }

    @Override
    public List<BookDto> getAll() {
        List<Book> bookList = em.createQuery("select b from Book b", Book.class).getResultList();
        return bookList.stream().map(e -> {
            em.detach(e);
            return bookMapper.toDto(e, BookDto.class);
        }).collect(Collectors.toList());
    }

    @Override
    public BookDto getById(long id) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.id = :id", Book.class);
        query.setParameter("id", id);
        Book book = query.getSingleResult();
        em.detach(book);
        return bookMapper.toDto(book, BookDto.class);
    }

    @Override
    public int update(BookDto book) {
        Query query = em.createQuery("update Book b set b.title = :title, b.author = :author, b.genre = :genre where b.id = :id");
        query.setParameter("id", book.getId());
        query.setParameter("title", book.getTitle());
        query.setParameter("author", authorMapper.toEntity(book.getAuthor(), Author.class));
        query.setParameter("genre", genreMapper.toEntity(book.getGenre(), Genre.class));
        return query.executeUpdate();
    }

    @Override
    public int deleteById(long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
