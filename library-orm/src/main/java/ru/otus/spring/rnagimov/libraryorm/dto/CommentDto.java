package ru.otus.spring.rnagimov.libraryorm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
@EqualsAndHashCode()
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private BookDto book;
    private String commentAuthor;
    private String text;
    private Timestamp created;

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return String.format("[%d] %s %s | %s: %s", id, simpleDateFormat.format(created), book.getTitle(), commentAuthor, text);
    }
}
