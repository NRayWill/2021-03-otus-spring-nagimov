package ru.otus.spring.rnagimov.libraryjpa.repository;

import ru.otus.spring.rnagimov.libraryjpa.model.Author;
import ru.otus.spring.rnagimov.libraryjpa.model.Book;
import ru.otus.spring.rnagimov.libraryjpa.model.Genre;

import java.sql.Timestamp;

public class LibraryTestUtils {

    public static final long EXISTING_BOOK_ID = 1001L;
    public static final String EXISTING_BOOK_TITLE = "Сто лет одиночества";

    public static final long EXISTING_AUTHOR_ID = 2001L;
    public static final String EXISTING_AUTHOR_NAME = "Габриэль";
    public static final String EXISTING_AUTHOR_MIDDLENAME = "Гарсиа";
    public static final String EXISTING_AUTHOR_SURNAME = "Маркес";

    public static final long EXISTING_GENRE_ID = 3001L;
    public static final String EXISTING_GENRE_NAME = "Роман";

    public static final long EXISTING_COMMENT_ID = 4001L;
    public static final String EXISTING_COMMENT_AUTHOR = "Wikipedia";
    public static final String EXISTING_COMMENT_TEXT = "Одно из наиболее характерных и популярных произведений в направлении магического реализма.";
    public static Timestamp EXISTING_COMMENT_DATE = Timestamp.valueOf("2021-07-30 17:25:00");

    private static Book EXISTING_BOOK;

    public static Book getExistingBook() {
        if (EXISTING_BOOK == null) {
            Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
            Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
            EXISTING_BOOK = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_TITLE, expectedAuthor, expectedGenre);
        }
        return EXISTING_BOOK;
    }
}
