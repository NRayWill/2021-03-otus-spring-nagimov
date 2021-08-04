package ru.otus.spring.rnagimov.libraryjpa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    // @Fetch(FetchMode.JOIN)
    @ManyToOne // (fetch = FetchType.EAGER)
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID")
    private Author author;

    // @Fetch(FetchMode.JOIN)
    @ManyToOne // (fetch = FetchType.EAGER)
    @JoinColumn(name = "GENRE_ID", referencedColumnName = "ID")
    private Genre genre;
}
