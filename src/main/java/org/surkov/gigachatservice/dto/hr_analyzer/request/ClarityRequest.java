package org.surkov.gigachatservice.dto.hr_analyzer.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO запроса для анализа ясности изложения (/chat/clarity).
 */
public record ClarityRequest(
        @NotBlank
        @Schema(description = "Полный текст резюме для проверки ясности", required = true)
        String text,

        @Schema(description = "Язык анализа (опционально, по умолчанию ru)")
        String language
) {
}
