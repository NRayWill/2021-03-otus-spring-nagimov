package ru.otus.spring.rnagimov.libraryjpa.mapper;

import org.modelmapper.ModelMapper;

import java.util.Objects;

public class CommonMapper<E, D> {

    private final ModelMapper mapper;

    public CommonMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public E toEntity(D dto, Class<E> entityClass) {
        return Objects.isNull(dto) ? null : mapper.map(dto, entityClass);
    }

    public D toDto(E entity, Class<D> dtoClass) {
        return Objects.isNull(entity) ? null : mapper.map(entity, dtoClass);
    }
}
