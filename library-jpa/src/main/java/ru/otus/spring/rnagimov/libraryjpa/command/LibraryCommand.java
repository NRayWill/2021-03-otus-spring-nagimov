package ru.otus.spring.rnagimov.libraryjpa.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.rnagimov.libraryjpa.dto.BookDto;
import ru.otus.spring.rnagimov.libraryjpa.dto.CommentDto;
import ru.otus.spring.rnagimov.libraryjpa.exception.NoElementWithSuchIdException;
import ru.otus.spring.rnagimov.libraryjpa.service.*;


@ShellComponent
public class LibraryCommand {

    public static final String BOOK_WITH_ID_MSG = "Book with ID = ";

    private final IoService ioService;
    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final CommentService commentService;

    public LibraryCommand(IoService ioService, BookService bookService, GenreService genreService, AuthorService authorService, CommentService commentService) {
        this.ioService = ioService;
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
        this.commentService = commentService;
    }

    // BOOK-commands

    @SuppressWarnings("unused")
    @ShellMethod(value = "Create book", key = {"createBook", "cb"})
    public void createBook(@ShellOption({"-T", "--title"}) String title,
                           @ShellOption({"-A", "--author"}) long authorId,
                           @ShellOption({"-G", "--genre"}) long genreId) throws NoElementWithSuchIdException {
        long bookId = bookService.createBook(title, authorId, genreId);
        ioService.printLn(BOOK_WITH_ID_MSG + bookId + " was created");
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Show all books", key = {"allBooks", "allb", "ab"})
    public void showAllBooks() {
        bookService.getAll().forEach(book -> ioService.printLn(book.toString()));
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Show book", key = {"showBook", "sb"})
    public void showBookById(@ShellOption long id) throws NoElementWithSuchIdException {
        BookDto book = bookService.getById(id);
        ioService.printLn(book != null? book.toString() : "Book with id=" + id + "doesn't exist");
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Update book", key = {"updateBook", "ub"})
    public void updateBook(@ShellOption(value = {"-I", "--id"}) Long id,
                           @ShellOption(value = {"-T", "--title"}, defaultValue = "") String title,
                           @ShellOption(value = {"-A", "--author"}, defaultValue = "") Long authorId,
                           @ShellOption(value = {"-G", "--genre"}, defaultValue = "") Long genreId) throws NoElementWithSuchIdException {
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
        authorService.getAll().forEach(author -> ioService.printLn(author.toString()));
    }

    // GENRE-commands

    @SuppressWarnings("unused")
    @ShellMethod(value = "Show all genres", key = {"allGenres", "allg", "ag"})
    public void showAllGenres() {
        genreService.getAll().forEach(genre -> ioService.printLn(genre.toString()));
    }

    // COMMENT-commands

    @SuppressWarnings("unused")
    @ShellMethod(value = "Create comment", key = {"createComment", "cc"})
    public void createComment(@ShellOption({"-B", "--bookId"}) long bookId,
                           @ShellOption({"-C", "--commentator"}) String commentAuthor,
                           @ShellOption({"-T", "--Text"}) String text) throws NoElementWithSuchIdException {
        long commentId = commentService.createComment(bookId, commentAuthor, text);
        ioService.printLn("Comment with ID = " + commentId + " was created");
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Show all comments", key = {"allComments", "allc", "ac"})
    public void showAllComments() {
        commentService.getAll().forEach(commentDto -> ioService.printLn(commentDto.toString()));
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Show comment", key = {"showComment", "sc"})
    public void showCommentById(@ShellOption long id) throws NoElementWithSuchIdException {
        CommentDto comment = commentService.getById(id);
        ioService.printLn(comment != null? comment.toString() : "Comment with id=" + id + "doesn't exist");
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Show comments by book", key = {"showBookComments", "sbc"})
    public void showCommentByBookId(@ShellOption long bookId) throws NoElementWithSuchIdException {
        commentService.getByBook(bookId).forEach(commentDto -> ioService.printLn(commentDto.toString()));
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Show comments by commentator", key = {"showPersonComments", "spc"})
    public void showCommentByPerson(@ShellOption String commentator) throws NoElementWithSuchIdException {
        commentService.getByCommentAuthor(commentator).forEach(commentDto -> ioService.printLn(commentDto.toString()));
    }
}
