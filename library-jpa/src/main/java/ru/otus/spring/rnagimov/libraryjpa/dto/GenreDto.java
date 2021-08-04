package ru.otus.spring.rnagimov.libraryjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {
    private Long id;
    private String name;

    @Override
    public String toString() {
        return "[" + id + "] " + name;
    }
}
