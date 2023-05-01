package com.es.phoneshop.web.dto.converter;

public interface DtoConverter<T, K> {
    public T convertToDto(K modelObject);
    public K convertToModel(T dtoObject);
}
