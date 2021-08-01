package ru.otus.spring.rnagimov.libraryorm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.rnagimov.libraryorm.dto.AuthorDto;
import ru.otus.spring.rnagimov.libraryorm.repository.AuthorRepository;
import ru.otus.spring.rnagimov.libraryorm.util.ConvertUtils;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional
    public List<AuthorDto> getAll() {
        return ConvertUtils.convertEntityListToDtoList(authorRepository.getAll(), AuthorDto.class);
    }
}
