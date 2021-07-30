package ru.otus.spring.rnagimov.libraryorm.model;

import lombok.*;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "AUTHOR")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MIDDLENAME")
    private String middleName;

    @Column(name = "SURNAME")
    private String surname;
}
