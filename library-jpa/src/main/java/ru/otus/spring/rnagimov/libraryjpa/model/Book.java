package ru.otus.spring.rnagimov.libraryjpa.model;

import lombok.*;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID")
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
