package ru.otus.spring.rnagimov.libraryjdbc.service;

import org.apache.logging.log4j.util.Strings;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.rnagimov.libraryjdbc.dao.AuthorDao;
import ru.otus.spring.rnagimov.libraryjdbc.dao.BookDao;
import ru.otus.spring.rnagimov.libraryjdbc.dao.GenreDao;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Author;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Book;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Genre;
import ru.otus.spring.rnagimov.libraryjdbc.exception.AmbiguousElementDefinitionException;
import ru.otus.spring.rnagimov.libraryjdbc.exception.NoSuchElementException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public long createBook(String title, long authorId, long genreId) {
        Author author = authorDao.getById(authorId);
        Genre genre = genreDao.getById(genreId);
        Book book = new Book(null, title, author, genre);
        return bookDao.insert(book);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Book getById(long id) throws NoSuchElementException, AmbiguousElementDefinitionException {
        try {
            return bookDao.getById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchElementException("Book doesn't exist");
        } catch (IncorrectResultSizeDataAccessException ex) {
            throw new AmbiguousElementDefinitionException("There are more than one book with id = " + id + " in database");
        }
    }

    @Override
    public void updateBook(long id, String title, Long authorId, Long genreId) {
        Book book = bookDao.getById(id);
        if (Strings.isNotEmpty(title)) {
            book.setTitle(title);
        }
        if (authorId != null){
            Author author = authorDao.getById(authorId);
            book.setAuthor(author);
        }
        if (genreId != null) {
            Genre genre = genreDao.getById(genreId);
            book.setGenre(genre);
        }
        bookDao.update(book);
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }
}
