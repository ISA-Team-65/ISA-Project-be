package com.team65.isaproject.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mapper<TModel, TDto> {

    private final ModelMapper modelMapper;

    public TModel MapToModel(TDto dto, Class<TModel> modelClass) {
        return modelMapper.map(dto, modelClass);
    }

    public TDto MapToDto(TModel model, Class<TDto> dtoClass) {
        return modelMapper.map(model, dtoClass);
    }
}
