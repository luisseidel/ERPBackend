package com.seidelsoft.ERPBackend.taskManager.model.dto;

import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record PdfDTO(
    String titulo,
    List<String> cabecalhos,
    List<Map<String, Object>> dados
) {
}
