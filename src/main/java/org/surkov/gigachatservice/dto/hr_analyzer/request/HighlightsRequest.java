package org.surkov.gigachatservice.dto.hr_analyzer.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO запроса для извлечения ключевых достижений из резюме (/chat/highlights).
 */
public record HighlightsRequest(
        @NotBlank
        @Schema(description = "Полный текст резюме для анализа достижений.", required = true)
        String text,

        @Schema(description = "Язык анализа (опционально, по умолчанию ru)")
        String language
) {
}
