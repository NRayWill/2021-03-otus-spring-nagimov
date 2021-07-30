package ru.otus.spring.rnagimov.libraryorm.model;

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
}
