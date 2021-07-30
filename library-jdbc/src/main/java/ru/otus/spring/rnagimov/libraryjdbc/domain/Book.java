package ru.otus.spring.rnagimov.libraryjdbc.domain;

import lombok.*;
import org.apache.logging.log4j.util.Strings;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    private String title;
    private Author author;
    private Genre genre;

    @Override
    public String toString() {
        String result = Strings.EMPTY;
        result += "[" + id.toString() + "] ";
        result += " " + author.getShortName() + ": ";
        result += "\"" + title + "\"";
        result += " (" + genre.getName() + ")";
        return result;
    }
}
