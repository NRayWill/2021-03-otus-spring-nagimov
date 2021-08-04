package ru.otus.spring.rnagimov.libraryorm.util;

import org.apache.commons.collections4.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.spring.rnagimov.libraryorm.mapper.CommonMapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConvertUtils {

    private static ModelMapper modelMapper;

    @Autowired
    private ConvertUtils(ModelMapper modelMapper) {
        ConvertUtils.modelMapper = modelMapper;
    }

    public static  <E, D> List<D> convertEntityListToDtoList(List<E> commentEntityList, Class<D> dtoClass) {
        if (CollectionUtils.isEmpty(commentEntityList)) {
            return List.of();
        }
        return commentEntityList.stream().map(e -> new CommonMapper<E, D>(modelMapper).toDto(e, dtoClass)).collect(Collectors.toList());
    }
}
