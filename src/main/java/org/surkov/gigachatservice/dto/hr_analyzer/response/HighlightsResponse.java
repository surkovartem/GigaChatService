package org.surkov.gigachatservice.dto.hr_analyzer.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * DTO ответа для выделения ключевых достижений (/chat/highlights).
 * Содержит список самых значимых достижений из резюме.
 */
public record HighlightsResponse(
        @Schema(description = "Список ключевых достижений кандидата")
        List<String> keyAchievements
) {
}
