package ru.otus.spring.rnagimov.libraryjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.rnagimov.libraryjpa.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {}
