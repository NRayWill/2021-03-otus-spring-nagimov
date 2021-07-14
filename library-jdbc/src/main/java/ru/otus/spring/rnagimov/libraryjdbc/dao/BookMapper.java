package ru.otus.spring.rnagimov.libraryjdbc.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Author;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Book;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        long bookId = resultSet.getLong("BOOK.ID");
        String bookTitle = resultSet.getString("TITLE");

        long authorId = resultSet.getLong("AUTHOR_ID");
        String authorName = resultSet.getString("AUTHOR.NAME");
        String authorSurname = resultSet.getString("SURNAME");
        String authorMiddleName = resultSet.getString("MIDDLENAME");
        Author author = new Author(authorId, authorName, authorMiddleName, authorSurname);

        long genreId = resultSet.getLong("GENRE_ID");
        String genreName = resultSet.getString("GENRE.NAME");
        Genre genre = new Genre(genreId, genreName);

        return new Book(bookId, bookTitle, author, genre);
    }
}
