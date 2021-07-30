package ru.otus.spring.rnagimov.libraryjdbc.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Author {
    private final Long id;
    private final String name;
    private final String middleName;
    private final String surname;

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
