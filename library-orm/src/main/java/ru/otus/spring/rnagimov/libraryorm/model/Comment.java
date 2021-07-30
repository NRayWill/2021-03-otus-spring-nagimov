package ru.otus.spring.rnagimov.libraryorm.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "COMMENT_AUTHOR")
    private String commentAuthor;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID")
    private Book book;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "CREATED")
    private Timestamp created;
}
