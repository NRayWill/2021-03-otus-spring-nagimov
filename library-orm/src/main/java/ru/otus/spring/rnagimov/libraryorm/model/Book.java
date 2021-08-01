package ru.otus.spring.rnagimov.libraryorm.model;

import lombok.*;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(name = "book-author-genre-entity-graph", attributeNodes = {@NamedAttributeNode(value = "author"), @NamedAttributeNode(value = "genre")})
@Table(name = "BOOK")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    // @Fetch(FetchMode.JOIN)
    @ManyToOne // (fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
    private Author author;

    // @Fetch(FetchMode.JOIN)
    @ManyToOne // (fetch = FetchType.EAGER)
    @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID")
    private Genre genre;
}
