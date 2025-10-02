package com.seidelsoft.ERPBackend.pdfGenerator.dto;

import java.util.List;
import java.util.Map;

public record PdfPayloadDTO(
        String titulo,
        List<String> cabecalhos,
        List<Map<String, Object>> dados
) {
}
