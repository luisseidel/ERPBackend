package com.seidelsoft.ERPBackend.system.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class JsonConverter {

    private final ObjectMapper objectMapper;

    @Autowired
    public JsonConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> String convertDtoToJson(T dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            log.error("Erro ao converter DTO para json: ", e);
        }
        return null;
    }

    public <T> T convertJsonToDto(String json, Class<T> targetClass) {
        try {
            return objectMapper.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            log.error("Erro ao converter json para dto: ", e);
        }
        return null;
    }

    public <T> List<T> convertJsonToListDto(String json, TypeReference<List<T>> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("Erro ao processar lista de dtos: ", e);
        }
        return null;
    }
}
