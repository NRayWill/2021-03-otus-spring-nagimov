package ru.otus.spring.rnagimov.libraryjpa.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.rnagimov.libraryjpa.dto.AuthorDto;
import ru.otus.spring.rnagimov.libraryjpa.mapper.CommonMapper;
import ru.otus.spring.rnagimov.libraryjpa.model.Author;
import ru.otus.spring.rnagimov.libraryjpa.repository.AuthorRepository;
import ru.otus.spring.rnagimov.libraryjpa.util.ConvertUtils;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> getAll() {
        return ConvertUtils.convertEntityListToDtoList(authorRepository.findAll(), AuthorDto.class);
    }
}
