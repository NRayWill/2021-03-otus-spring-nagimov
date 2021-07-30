package ru.otus.spring.rnagimov.libraryorm.dto;

import lombok.*;
import org.apache.logging.log4j.util.Strings;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private Long id;
    private String name;
    private String middleName;
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
