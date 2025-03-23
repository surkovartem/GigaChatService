package org.surkov.gigachatservice.dto;

import lombok.Data;

/**
 * DTO для сообщения в запросе к GigaChat API.
 */
@Data
public class GigaChatMessage {
    private String role;
    private String content;
}
