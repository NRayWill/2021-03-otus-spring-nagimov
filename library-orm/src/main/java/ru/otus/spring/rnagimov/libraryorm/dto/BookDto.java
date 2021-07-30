package ru.otus.spring.rnagimov.libraryorm.dto;

import lombok.*;
import org.apache.logging.log4j.util.Strings;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private AuthorDto author;
    private GenreDto genre;

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
