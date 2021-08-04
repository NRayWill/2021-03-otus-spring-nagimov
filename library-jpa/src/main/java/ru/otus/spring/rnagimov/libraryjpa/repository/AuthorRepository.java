package ru.otus.spring.rnagimov.libraryjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.rnagimov.libraryjpa.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {}
