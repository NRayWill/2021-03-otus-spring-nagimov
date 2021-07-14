package ru.otus.spring.rnagimov.libraryjdbc.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Genre {
    private final Long id;
    private final String name;

    @Override
    public String toString() {
        return "[" + id + "] " + name;
    }
}
