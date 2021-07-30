package ru.otus.spring.rnagimov.libraryjpa.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.rnagimov.libraryjpa.exception.AmbiguousElementDefinitionException;
import ru.otus.spring.rnagimov.libraryjpa.exception.NoElementWithSuchIdException;
import ru.otus.spring.rnagimov.libraryjpa.model.Author;
import ru.otus.spring.rnagimov.libraryjpa.model.Book;
import ru.otus.spring.rnagimov.libraryjpa.model.Genre;
import ru.otus.spring.rnagimov.libraryjpa.repository.AuthorRepository;
import ru.otus.spring.rnagimov.libraryjpa.repository.BookRepository;
import ru.otus.spring.rnagimov.libraryjpa.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

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
        Author author = authorRepository.getById(authorId);
        Genre genre = genreRepository.getById(genreId);
        Book book = new Book(null, title, author, genre);
        return bookRepository.save(book).getId();
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book getById(long id) throws NoElementWithSuchIdException, AmbiguousElementDefinitionException {
        try {
            Optional<Book> bookOpt = bookRepository.findById(id);
            if (bookOpt.isPresent()) {
                return bookOpt.get();
            } else {
                throw new NoElementWithSuchIdException("Book doesn't exist");
            }
        } catch (IncorrectResultSizeDataAccessException ex) {
            throw new AmbiguousElementDefinitionException("There are more than one book with id = " + id + " in database");
        }
    }

    @Override
    @Transactional
    public void updateBook(long id, String title, Long authorId, Long genreId) {
        Book book = bookRepository.getById(id);
        if (Strings.isNotEmpty(title)) {
            book.setTitle(title);
        }
        if (authorId != null){
            Author author = authorRepository.getById(authorId);
            book.setAuthor(author);
        }
        if (genreId != null) {
            Genre genre = genreRepository.getById(genreId);
            book.setGenre(genre);
        }
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }
}
