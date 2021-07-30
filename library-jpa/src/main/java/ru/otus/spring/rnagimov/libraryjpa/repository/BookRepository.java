package ru.otus.spring.rnagimov.libraryjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.rnagimov.libraryjpa.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
