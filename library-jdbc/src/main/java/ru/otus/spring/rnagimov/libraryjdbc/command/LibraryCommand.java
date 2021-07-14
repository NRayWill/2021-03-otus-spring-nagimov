package ru.otus.spring.rnagimov.libraryjdbc.command;

import ru.otus.spring.rnagimov.libraryjdbc.exception.AmbiguousRowDefinitionException;
import ru.otus.spring.rnagimov.libraryjdbc.exception.RowAlreadyExistsException;
import ru.otus.spring.rnagimov.libraryjdbc.exception.NoSuchRowException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.rnagimov.libraryjdbc.dao.AuthorDao;
import ru.otus.spring.rnagimov.libraryjdbc.dao.GenreDao;
import ru.otus.spring.rnagimov.libraryjdbc.service.BookService;
import ru.otus.spring.rnagimov.libraryjdbc.service.IoService;

import java.util.Optional;


@ShellComponent
public class LibraryCommand {

    public static final String BOOK_WITH_ID_MSG = "Book with ID = ";
    private final IoService ioService;
    private final BookService bookService;

    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public LibraryCommand(IoService ioService, BookService bookService, AuthorDao authorDao, GenreDao genreDao) {
        this.ioService = ioService;
        this.bookService = bookService;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    // BOOK-commands

    @SuppressWarnings("unused")
    @ShellMethod(value = "Create book", key = {"createBook", "cb"})
    public void createBook(@ShellOption(value = {"-I", "--id"}, defaultValue = "") Long id,
                           @ShellOption({"-T", "--title"}) String title,
                           @ShellOption({"-A", "--author"}) long authorId,
                           @ShellOption({"-G", "--genre"}) long genreId) throws RowAlreadyExistsException {
        Optional<Long> bookId = bookService.createBook(id, title, authorId, genreId);
        ioService.printLn(BOOK_WITH_ID_MSG + (bookId.isEmpty() ? id : bookId.get()) + " was created");
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Show all books", key = {"allBooks", "allb", "ab"})
    public void showAllBooks() {
        bookService.getAll().forEach(book -> ioService.printLn(book.toString()));
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Show book", key = {"showBook", "sb"})
    public void showBookById(@ShellOption long id) throws NoSuchRowException, AmbiguousRowDefinitionException {
        ioService.printLn(bookService.getById(id).toString());
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Update book", key = {"updateBook", "ub"})
    public void updateBook(@ShellOption(value = {"-I", "--id"}) Long id,
                           @ShellOption(value = {"-T", "--title"}, defaultValue = "") String title,
                           @ShellOption(value = {"-A", "--author"}, defaultValue = "") Long authorId,
                           @ShellOption(value = {"-G", "--genre"}, defaultValue = "") Long genreId) {
        bookService.updateBook(id, title, authorId, genreId);
        ioService.printLn(BOOK_WITH_ID_MSG + id + " was updated");
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Remove book", key = {"removeBook", "remBook", "rb"})
    public void deleteBookById(@ShellOption long id) {
        bookService.deleteById(id);
        ioService.printLn(BOOK_WITH_ID_MSG + id + " was removed");
    }

    // AUTHOR-commands

    @SuppressWarnings("unused")
    @ShellMethod(value = "Show all authors", key = {"allAuthors", "alla", "aa"})
    public void showAllAuthors() {
        authorDao.getAll().forEach(author -> ioService.printLn(author.toString()));
    }

    // GENRE-commands

    @SuppressWarnings("unused")
    @ShellMethod(value = "Show all genres", key = {"allGenres", "allg", "ag"})
    public void showAllGenres() {
        genreDao.getAll().forEach(genre -> ioService.printLn(genre.toString()));
    }

}
