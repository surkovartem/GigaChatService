package org.surkov.gigachatservice.dto.hr_analyzer.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO запроса для анализа стабильности карьеры (/chat/stability).
 */
public record StabilityRequest(
        @NotBlank
        @Schema(description = "Полный текст резюме кандидата", required = true)
        String text,

        @Schema(description = "Язык анализа (опционально, по умолчанию ru)")
        String language
) {
}
