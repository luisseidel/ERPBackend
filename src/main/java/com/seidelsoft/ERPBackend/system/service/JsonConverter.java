package com.seidelsoft.ERPBackend.system.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JsonConverter {

    private final ObjectMapper objectMapper;

    @Autowired
    public JsonConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> String convertDtoToJson(T dto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(dto);
    }

    public <T> T convertJsonToDto(String json, Class<T> targetClass) throws JsonProcessingException {
        return objectMapper.readValue(json, targetClass);
    }

    public <T> List<T> convertJsonToListDto(String json, TypeReference<List<T>> typeReference) throws JsonProcessingException {
        return objectMapper.readValue(json, typeReference);
    }
}
