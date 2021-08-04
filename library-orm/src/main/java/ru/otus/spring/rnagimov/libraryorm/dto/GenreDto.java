package ru.otus.spring.rnagimov.libraryorm.dto;

import lombok.*;

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
