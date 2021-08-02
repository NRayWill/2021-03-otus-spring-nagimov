package ru.otus.spring.rnagimov.libraryorm.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.rnagimov.libraryorm.dto.GenreDto;
import ru.otus.spring.rnagimov.libraryorm.repository.GenreRepository;
import ru.otus.spring.rnagimov.libraryorm.util.ConvertUtils;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> getAll() {
        return ConvertUtils.convertEntityListToDtoList(genreRepository.getAll(), GenreDto.class);
    }
}
