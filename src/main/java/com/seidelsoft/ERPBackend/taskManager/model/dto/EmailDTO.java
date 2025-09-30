package com.seidelsoft.ERPBackend.taskManager.model.dto;

import lombok.Builder;

@Builder
public record EmailDTO(
    String from,
    String destination,
    String subject,
    String body
) {
}
