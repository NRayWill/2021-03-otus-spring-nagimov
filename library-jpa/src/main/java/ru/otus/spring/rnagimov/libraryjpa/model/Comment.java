package ru.otus.spring.rnagimov.libraryjpa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "COMMENT")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID")
    private Book book;

    @Column(name = "COMMENT_AUTHOR")
    private String commentAuthor;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "CREATED")
    private Date created;
}
