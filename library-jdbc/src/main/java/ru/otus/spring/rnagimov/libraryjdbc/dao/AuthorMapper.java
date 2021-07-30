package ru.otus.spring.rnagimov.libraryjdbc.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.spring.rnagimov.libraryjdbc.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String middleName = resultSet.getString("middlename");
        return new Author(id, name, middleName, surname);
    }
}
