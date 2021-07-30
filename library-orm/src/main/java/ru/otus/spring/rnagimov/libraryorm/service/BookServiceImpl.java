package ru.otus.spring.rnagimov.libraryorm.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.rnagimov.libraryorm.repository.AuthorRepository;
import ru.otus.spring.rnagimov.libraryorm.repository.BookRepository;
import ru.otus.spring.rnagimov.libraryorm.repository.GenreRepository;
import ru.otus.spring.rnagimov.libraryorm.dto.AuthorDto;
import ru.otus.spring.rnagimov.libraryorm.dto.BookDto;
import ru.otus.spring.rnagimov.libraryorm.dto.GenreDto;
import ru.otus.spring.rnagimov.libraryorm.exception.AmbiguousElementDefinitionException;
import ru.otus.spring.rnagimov.libraryorm.exception.NoElementWithSuchIdException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional
    public long createBook(String title, long authorId, long genreId) {
        AuthorDto author = authorRepository.getById(authorId);
        GenreDto genre = genreRepository.getById(genreId);
        BookDto book = new BookDto(null, title, author, genre);
        return bookRepository.insert(book);
    }

    @Override
    public List<BookDto> getAll() {
        return bookRepository.getAll();
    }

    @Override
    public BookDto getById(long id) throws NoElementWithSuchIdException, AmbiguousElementDefinitionException {
        try {
            return bookRepository.getById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoElementWithSuchIdException("Book doesn't exist");
        } catch (IncorrectResultSizeDataAccessException ex) {
            throw new AmbiguousElementDefinitionException("There are more than one book with id = " + id + " in database");
        }
    }

    @Override
    @Transactional
    public void updateBook(long id, String title, Long authorId, Long genreId) {
        BookDto book = bookRepository.getById(id);
        if (Strings.isNotEmpty(title)) {
            book.setTitle(title);
        }
        if (authorId != null){
            AuthorDto author = authorRepository.getById(authorId);
            book.setAuthor(author);
        }
        if (genreId != null) {
            GenreDto genre = genreRepository.getById(genreId);
            book.setGenre(genre);
        }
        bookRepository.update(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
