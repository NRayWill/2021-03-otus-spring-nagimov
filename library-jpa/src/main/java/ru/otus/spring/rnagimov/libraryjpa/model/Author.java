package ru.otus.spring.rnagimov.libraryjpa.model;

import lombok.*;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public String getShortName() {
        return surname + " "
                + name.charAt(0) + "."
                + (Strings.isNotEmpty(middleName) ? (middleName.charAt(0) + ".") : Strings.EMPTY);
    }

    @Override
    public String toString() {
        return "[" + id + "] "
                + name + " "
                + (Strings.isNotEmpty(middleName) ? (middleName + " ") : Strings.EMPTY)
                + surname;
    }
}
